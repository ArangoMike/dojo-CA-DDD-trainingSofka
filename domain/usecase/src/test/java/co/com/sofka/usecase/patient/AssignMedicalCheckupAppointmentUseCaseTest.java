package co.com.sofka.usecase.patient;

import co.com.sofka.model.events.gateways.EventBus;
import co.com.sofka.model.generic.DomainEvent;
import co.com.sofka.model.patient.events.AppointmentAssociated;
import co.com.sofka.model.patient.events.EnablePatientModified;
import co.com.sofka.model.patient.events.MedicalCheckupAppointmentAssigned;
import co.com.sofka.model.patient.events.PatientCreated;
import co.com.sofka.model.patient.values.Email;
import co.com.sofka.model.patient.values.FullName;
import co.com.sofka.model.patient.values.TypeId;
import co.com.sofka.usecase.gateways.DomainEventRepository;
import co.com.sofka.usecase.gateways.PatientRepository;
import co.com.sofka.usecase.patient.commands.AssignMedicalCheckupAppointmentCommand;
import co.com.sofka.usecase.patient.commands.ModifyEmailPatientCommand;
import co.com.sofka.usecase.patient.commands.ModifyEnablePatientCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

class AssignMedicalCheckupAppointmentUseCaseTest {

    @Mock
    private DomainEventRepository repository;
    @Mock
    private PatientRepository patientRepository;
    @Mock
    private EventBus bus;
    private AssignMedicalCheckupAppointmentUseCase useCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = new AssignMedicalCheckupAppointmentUseCase(repository,patientRepository, bus);
    }

    @Test
    void apply() {

        String patientId = "patientId";
        String appointmentId = "appointmentId";
        String medicalCheckup = "revision medical incapacidad";

        AssignMedicalCheckupAppointmentCommand command = new AssignMedicalCheckupAppointmentCommand(
                patientId,appointmentId,medicalCheckup);

        MedicalCheckupAppointmentAssigned eventRes = new MedicalCheckupAppointmentAssigned(
                patientId,appointmentId,medicalCheckup);

        Mockito.when(repository.findById(any(String.class))).thenReturn(events());
        Mockito.when(patientRepository.assignMedicalCheckupByAppointmentById(
                any(AssignMedicalCheckupAppointmentCommand.class))).thenReturn(Mono.empty());
        Mockito.when(repository.saveEvent(any(MedicalCheckupAppointmentAssigned.class)))
                .thenReturn(Mono.just(eventRes));
        Mockito.doAnswer(i -> null).when(bus).publish(ArgumentMatchers.any(DomainEvent.class),eq("email"));

        //Act
        Flux<DomainEvent> result = useCase.apply(Mono.just(command));

        StepVerifier.create(result)
                //Assert
                .expectNextMatches(event1 -> event1 instanceof MedicalCheckupAppointmentAssigned &&
                        ((MedicalCheckupAppointmentAssigned) event1).getPatientId().equals(command.getPatientId()) &&
                        ((MedicalCheckupAppointmentAssigned) event1).getMedicalCheckup().equals(command.getMedicalCheckup())

                )
                .expectComplete()
                .verify();


    }

    private Flux<DomainEvent> events() {
        return Flux.just(new PatientCreated(
                new FullName("Test Patient").value(),new TypeId("CC").value(), new Email("test@test.com").value()),
                new AppointmentAssociated("2023/03/13 10:00","appointmentId"));
    }

}