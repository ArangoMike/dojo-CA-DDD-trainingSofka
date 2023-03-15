package co.com.sofka.usecase.patient;

import co.com.sofka.model.generic.DomainEvent;
import co.com.sofka.model.patient.Patient;

import co.com.sofka.model.patient.values.*;
import co.com.sofka.usecase.gateways.DomainEventRepository;
import co.com.sofka.usecase.gateways.PatientRepository;
import co.com.sofka.usecase.generic.UseCaseForCommand;
import co.com.sofka.usecase.patient.commands.CreatePatientCommand;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CreatePatientUseCase extends UseCaseForCommand<CreatePatientCommand> {

    private final DomainEventRepository repository;
    private final PatientRepository patientRepository;

    public CreatePatientUseCase(DomainEventRepository repository, PatientRepository patientRepository) {
        this.repository = repository;
        this.patientRepository = patientRepository;
    }

    @Override
    public Flux<DomainEvent> apply(Mono<CreatePatientCommand> createPatientCommandMono) {
        return createPatientCommandMono.flatMapIterable(command ->{
            Patient patient = new Patient(PatientId.of(command.getPatientId()),
                    new FullName(command.getFullName()),
                    new TypeId(command.getTypeId()),
                    new Enable("true"),
                    new Email(command.getEmail()));

              patientRepository.createPatient(new CreatePatientCommand(command.getPatientId(),
                            command.getFullName(), command.getTypeId(), command.getEmail())).subscribe();

           return patient.getUncommittedChanges();
        }).flatMap(event -> {
            if (event == null) {
                return Mono.empty();
            }
            return repository.saveEvent(event);
        }).map(event -> {
            return event;
        });
    }
}
