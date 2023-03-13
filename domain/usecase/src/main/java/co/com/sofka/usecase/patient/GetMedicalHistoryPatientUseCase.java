package co.com.sofka.usecase.patient;

import co.com.sofka.usecase.gateways.PatientRepository;
import co.com.sofka.usecase.patient.commands.CreatePatientCommand;
import reactor.core.publisher.Mono;

public class GetMedicalHistoryPatientUseCase {

    private final PatientRepository patientRepository;

    public GetMedicalHistoryPatientUseCase(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Mono<CreatePatientCommand> apply(String id){return patientRepository.findById(id);}
}
