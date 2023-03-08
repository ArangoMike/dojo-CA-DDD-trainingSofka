package co.com.sofka.usecase.patient;

import co.com.sofka.model.generic.DomainEvent;
import co.com.sofka.model.patient.Patient;
import co.com.sofka.model.patient.values.FullName;
import co.com.sofka.model.patient.values.PatientId;
import co.com.sofka.model.patient.values.TypeId;
import co.com.sofka.usecase.gateways.DomainEventRepository;
import co.com.sofka.usecase.generic.UseCaseForCommand;
import co.com.sofka.usecase.patient.commands.CreatePatientCommand;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CreatePatientUseCase extends UseCaseForCommand<CreatePatientCommand> {


    private final DomainEventRepository repository;


    public CreatePatientUseCase(DomainEventRepository repository) {
        this.repository = repository;

    }

    @Override
    public Flux<DomainEvent> apply(Mono<CreatePatientCommand> createPatientCommandMono) {
        return createPatientCommandMono.flatMapIterable(command ->{
            Patient patient = new Patient(PatientId.of(command.getPatientId()),
                    new FullName(command.getFullName()),
                    new TypeId(command.getTypeId()));
            return patient.getUncommittedChanges();
        }).flatMap(event -> {
          return  repository.saveEvent(event);
            }).map(event -> {
            return event;
        });
    }
}
