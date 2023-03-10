package co.com.sofka.usecase.patient;

import co.com.sofka.model.generic.DomainEvent;
import co.com.sofka.usecase.gateways.DomainEventRepository;
import co.com.sofka.usecase.gateways.PatientRepository;
import co.com.sofka.usecase.generic.UseCaseForCommand;
import co.com.sofka.usecase.patient.commands.UpdatePatientCommand;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class UpdatePatientUseCase extends UseCaseForCommand<UpdatePatientCommand> {

    private final DomainEventRepository repository;
    private final PatientRepository patientRepository;

    public UpdatePatientUseCase(DomainEventRepository repository, PatientRepository patientRepository) {
        this.repository = repository;
        this.patientRepository = patientRepository;
    }

    @Override
    public Flux<DomainEvent> apply(Mono<UpdatePatientCommand> updatePatientCommandMono) {
        return null;
    }
}
