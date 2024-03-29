package co.com.sofka.usecase.gateways;

import co.com.sofka.usecase.patient.commands.*;
import reactor.core.publisher.Mono;

public interface PatientRepository {
    Mono<Void> createPatient(CreatePatientCommand patientCreated);

    Mono<Void> modifyEnablePatient(ModifyEnablePatientCommand modifyEnablePatientCommand);

    Mono<Void> modifyEmailPatient(ModifyEmailPatientCommand modifyEmailPatientCommand);

    Mono<Void> addAppointmentPatient(AssociateAppointmentCommand associateAppointmentCommand);

    Mono<String> findEmailById(String id);

    Mono<CreatePatientCommand> findById(String id);

    Mono<Void> assignMedicalCheckupByAppointmentById(AssignMedicalCheckupAppointmentCommand assignMedicalCheckupAppointmentCommand);
}
