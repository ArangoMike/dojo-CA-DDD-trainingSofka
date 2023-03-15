package co.com.sofka.usecase.patient;

import co.com.sofka.model.agenda.events.AgendaDayScheduleAssigned;
import co.com.sofka.model.events.gateways.EventBus;
import co.com.sofka.model.generic.DomainEvent;
import co.com.sofka.model.patient.Patient;
import co.com.sofka.model.patient.events.AppointmentAssociated;
import co.com.sofka.model.patient.events.PatientCreated;
import co.com.sofka.model.patient.values.*;
import co.com.sofka.usecase.gateways.DomainEventRepository;
import co.com.sofka.usecase.gateways.PatientRepository;
import co.com.sofka.usecase.patient.commands.AssociateAppointmentCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;


class AssociateAppointmentUseCaseTest {


    @Mock
    private DomainEventRepository repository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private EventBus bus;

    private AssociateAppointmentUseCase useCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = new AssociateAppointmentUseCase(repository, patientRepository, bus);
    }


    @Test
    public void Apply() {

        AssociateAppointmentCommand  event = new AssociateAppointmentCommand(
                "agenda","pacient", "2023/03/13 10:00"
        );

        AppointmentAssociated appointmentAssociated = new AppointmentAssociated("2023/03/13 10:00","apid");

        Mockito.when(repository.findById(any(String.class))).thenReturn(events());
        Mockito.when(patientRepository.addAppointmentPatient(any(AssociateAppointmentCommand.class))).thenReturn(Mono.empty());
        Mockito.when(repository.saveEvent(any(AppointmentAssociated.class))).thenReturn(Mono.just(appointmentAssociated));
        Mockito.when(patientRepository.findEmailById(any(String.class))).thenReturn(Mono.empty());
        Mockito.doAnswer(i -> null).when(bus).publish(ArgumentMatchers.any(DomainEvent.class),eq("email"));

        //Act
        Flux<DomainEvent> result = useCase.apply(Mono.just(event));

        StepVerifier.create(result)
                //Assert
                .expectNextMatches(event1 -> event1 instanceof AppointmentAssociated &&
                        ((AppointmentAssociated) event1).getAppointmentDate().equals("2023/03/13 10:00") &&
                        ((AppointmentAssociated) event1).getAppointmentId().equals("apid")
                )
                .expectComplete()
                .verify();
    }

    private Flux<DomainEvent> events() {
        return Flux.just(new PatientCreated(
                new FullName("Test Patient").value(),new TypeId("CC").value(), new Email("test@test.com").value()));
    }

}
