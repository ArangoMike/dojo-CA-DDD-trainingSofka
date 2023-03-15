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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

class AssociateAppointmentEventUseCaseTest {

    @Mock
    private DomainEventRepository repository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private EventBus bus;

    private AssociateAppointmentEventUseCase useCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = new AssociateAppointmentEventUseCase(repository, patientRepository, bus);
    }


    @Test
    public void Apply() {

        String AGGREGATE_ID = "testid";
        AgendaDayScheduleAssigned event = new AgendaDayScheduleAssigned(
                "agenda","pacient", "2023/03/13 10:00"
        );
        event.setAggregateRootId(AGGREGATE_ID);

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




/*
    @Test
    public void Apply() {
        // Given
        String patientId = "123";
        String agendaId = "agenda1";
        String appointmentDate = "2022-05-01";
        String email = "test@test.com";
        String fullname = "fullname";
        AppointmentId appointmentId = new AppointmentId();
        AssociateAppointmentCommand associateAppointmentCommand = new AssociateAppointmentCommand(appointmentId.value(), patientId, appointmentDate);
        AgendaDayScheduleAssigned agendaDayScheduleAssigned = new AgendaDayScheduleAssigned(agendaId,patientId, appointmentDate);
        AppointmentAssociated appointmentAssociated = new AppointmentAssociated(appointmentDate,appointmentId.value());

        Patient patient = new Patient(PatientId.of(patientId),new FullName(fullname),new TypeId("CC"),new Enable("true"),new Email(email));
        patient.AssociateAppointment(new AppointmentDate(appointmentDate), appointmentId);
        patient.getUncommittedChanges();


        when(repository.findById(any(String.class))).thenReturn(events());
        when(patientRepository.addAppointmentPatient(any(AssociateAppointmentCommand.class))).thenReturn(Mono.empty());
        when(patientRepository.findEmailById(any(String.class))).thenReturn(Mono.just(email));
        when(repository.saveEvent(any(DomainEvent.class))).thenReturn(Mono.just(appointmentAssociated));

        // When
        Flux<DomainEvent> result = useCase.apply(Mono.just(agendaDayScheduleAssigned));

        // Then
        verify(repository).findById(patientId);
        verify(patientRepository).addAppointmentPatient(associateAppointmentCommand);
        verify(patientRepository).findEmailById(patientId);
        verify(repository).saveEvent(appointmentAssociated);
        verify(bus).publish(appointmentAssociated, "test@test.com");
        result.collectList().block();
    }



    private Flux<DomainEvent> events() {
        return Flux.just(new PatientCreated(
                new FullName("Test Patient").value(),new TypeId("CC").value(), new Email("test@test.com").value()));
    }

 */

