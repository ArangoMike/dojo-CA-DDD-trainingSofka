package co.com.sofka.usecase.gateways;


import co.com.sofka.usecase.patient.commands.AssociateAppointmentCommand;
import co.com.sofka.usecase.patient.commands.CreatePatientCommand;
import co.com.sofka.usecase.patient.commands.ModifyEmailPatientCommand;
import co.com.sofka.usecase.patient.commands.ModifyEnablePatientCommand;
import reactor.core.publisher.Mono;

public interface PatientRepository {
    Mono<CreatePatientCommand> createPatient(CreatePatientCommand patientCreated);

    Mono<CreatePatientCommand> modifyEnablePatient(ModifyEnablePatientCommand modifyEnablePatientCommand);

    Mono<CreatePatientCommand> modifyEmailPatient(ModifyEmailPatientCommand modifyEmailPatientCommand);

    Mono<CreatePatientCommand> addAppointmentPatient(AssociateAppointmentCommand associateAppointmentCommand);

    Mono<String> findEmailById(String id);
}
