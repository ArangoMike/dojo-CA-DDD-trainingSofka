package co.com.sofka.usecase.gateways;


import co.com.sofka.usecase.patient.commands.AssociateAppointmentCommand;
import co.com.sofka.usecase.patient.commands.CreatePatientCommand;
import co.com.sofka.usecase.patient.commands.ModifyEnableCommand;
import reactor.core.publisher.Mono;

public interface PatientRepository {
    Mono<CreatePatientCommand> createPatient(CreatePatientCommand patientCreated );

    Mono<CreatePatientCommand> updateEnablePacient(ModifyEnableCommand modifyEnableCommand);

    Mono<CreatePatientCommand> addAppointmentPacient(AssociateAppointmentCommand associateAppointmentCommand);

    Mono<String> findEmailById(String id);
}
