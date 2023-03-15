package co.com.sofka.usecase.gateways;

import co.com.sofka.usecase.patient.commands.*;
import reactor.core.publisher.Mono;

public interface PatientRepository {
    Mono<Void> createPatient(CreatePatientCommand patientCreated);

    Mono<CreatePatientCommand> modifyEnablePatient(ModifyEnablePatientCommand modifyEnablePatientCommand);

    Mono<CreatePatientCommand> modifyEmailPatient(ModifyEmailPatientCommand modifyEmailPatientCommand);

    Mono<Void> addAppointmentPatient(AssociateAppointmentCommand associateAppointmentCommand);

    Mono<String> findEmailById(String id);

    Mono<CreatePatientCommand> findById(String id);

    Mono<CreatePatientCommand> assignMedicalCheckupByAppointmentById(AssignMedicalCheckupAppointmentCommand assignMedicalCheckupAppointmentCommand);
}
