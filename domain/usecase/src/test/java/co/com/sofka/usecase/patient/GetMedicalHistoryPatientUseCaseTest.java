package co.com.sofka.usecase.patient;

import co.com.sofka.usecase.gateways.PatientRepository;
import co.com.sofka.usecase.patient.commands.AssociateAppointmentCommand;
import co.com.sofka.usecase.patient.commands.CreatePatientCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;


import static org.mockito.ArgumentMatchers.any;

class GetMedicalHistoryPatientUseCaseTest {
    @Mock
    private PatientRepository repository;
    private GetMedicalHistoryPatientUseCase useCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = new GetMedicalHistoryPatientUseCase(repository);
    }

    @Test
    void apply() {
        String patientId = "123";
        String fullName = "nombre paciente";
        String typeId = "CC";
        String email = "kjkj.test@sofka.com.co";

        List appointments = new ArrayList<>();
        AssociateAppointmentCommand appointment = new AssociateAppointmentCommand("appointment",patientId,"2023/03/13 08:00");
        AssociateAppointmentCommand appointment1 = new AssociateAppointmentCommand("appointment1",patientId,"2023/03/13 09:00");
        CreatePatientCommand command = new CreatePatientCommand(patientId, fullName, typeId, email);
        appointments.add(appointment);
        appointments.add(appointment1);
        command.setAppointments(appointments);

        Mockito.when(repository.findById(any(String.class))).thenReturn(Mono.just(command));

        //Act
        Mono<CreatePatientCommand> result = useCase.apply(patientId);

        StepVerifier.create(result)
                //Assert
                .expectNextMatches(event1 -> event1 instanceof CreatePatientCommand &&
                        event1.getPatientId().equals(command.getPatientId()) &&
                        event1.getEnable().equals(command.getEnable()) &&
                        event1.getEmail().equals(command.getEmail())
                )
                .expectComplete()
                .verify();

    }
}