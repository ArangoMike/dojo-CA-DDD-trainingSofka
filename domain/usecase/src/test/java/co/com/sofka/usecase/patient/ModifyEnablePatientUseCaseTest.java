package co.com.sofka.usecase.patient;

import co.com.sofka.model.generic.DomainEvent;
import co.com.sofka.model.patient.events.AppointmentAssociated;
import co.com.sofka.model.patient.events.EnablePatientModified;
import co.com.sofka.model.patient.events.PatientCreated;
import co.com.sofka.model.patient.values.Email;
import co.com.sofka.model.patient.values.FullName;
import co.com.sofka.model.patient.values.TypeId;
import co.com.sofka.usecase.agenda.AssociatePatientEventUseCase;
import co.com.sofka.usecase.gateways.DomainEventRepository;
import co.com.sofka.usecase.gateways.PatientRepository;
import co.com.sofka.usecase.patient.commands.AssociateAppointmentCommand;
import co.com.sofka.usecase.patient.commands.ModifyEnablePatientCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class ModifyEnablePatientUseCaseTest {

    @Mock
    private DomainEventRepository repository;

    @Mock
    private PatientRepository patientRepository;
    private ModifyEnablePatientUseCase useCase;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = new ModifyEnablePatientUseCase(repository,patientRepository);
    }

    @Test
    void apply() {

        String patientId = "patientId";
        String enable = "false";

        ModifyEnablePatientCommand command = new ModifyEnablePatientCommand(patientId,enable);

        EnablePatientModified enablePatientModified = new EnablePatientModified(patientId, enable);

        Mockito.when(repository.findById(any(String.class))).thenReturn(events());
        Mockito.when(patientRepository.modifyEnablePatient(any(ModifyEnablePatientCommand.class))).thenReturn(Mono.empty());
        Mockito.when(repository.saveEvent(any(EnablePatientModified.class))).thenReturn(Mono.just(enablePatientModified));

        //Act
        Flux<DomainEvent> result = useCase.apply(Mono.just(command));

        StepVerifier.create(result)
                //Assert
                .expectNextMatches(event1 -> event1 instanceof EnablePatientModified &&
                        ((EnablePatientModified) event1).getPatientId().equals(command.getPatientId()) &&
                        ((EnablePatientModified) event1).getEnable().equals(command.getEnable())

                )
                .expectComplete()
                .verify();
    }

    private Flux<DomainEvent> events() {
        return Flux.just(new PatientCreated(
                new FullName("Test Patient").value(),new TypeId("CC").value(), new Email("test@test.com").value()));
    }

}