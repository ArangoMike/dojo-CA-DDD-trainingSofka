package co.com.sofka.usecase.patient;

import co.com.sofka.model.generic.DomainEvent;

import co.com.sofka.model.patient.events.PatientCreated;
import co.com.sofka.usecase.gateways.DomainEventRepository;
import co.com.sofka.usecase.gateways.PatientRepository;
import co.com.sofka.usecase.patient.commands.CreatePatientCommand;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreatePatientUseCaseTest {

    private CreatePatientUseCase createPatientUseCase;
    private DomainEventRepository eventRepository;
    private PatientRepository patientRepository;

    @BeforeEach
    void setUp() {
        eventRepository = mock(DomainEventRepository.class);
        patientRepository = mock(PatientRepository.class);
        createPatientUseCase = new CreatePatientUseCase(eventRepository, patientRepository);
    }

    @Test
    void apply() {
        // Arrange
        String patientId = "123";
        String fullName = "Adryan Ynfante";
        String typeId = "CC";
        String email = "kjkj.test@sofka.com.co";
        CreatePatientCommand command = new CreatePatientCommand(patientId, fullName, typeId, email);
        PatientCreated patientCreated = new PatientCreated(fullName,typeId,email);

        // Configurar el comportamiento esperado del método createPatient
        when(patientRepository.createPatient(any(CreatePatientCommand.class))).thenReturn(Mono.empty());
        when(eventRepository.saveEvent(any(DomainEvent.class))).thenReturn(Mono.just(patientCreated));

        // Act
        Flux<DomainEvent> result = createPatientUseCase.apply(Mono.just(command));

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(event -> event instanceof PatientCreated &&
                        ((PatientCreated) event).getFullName().equals(fullName) &&
                        ((PatientCreated) event).getEnable().equals("true") &&
                        ((PatientCreated) event).getTypeId().equals(typeId) &&
                        ((PatientCreated) event).getEmail().equals(email))
                .expectComplete()
                .verify();

    }
}