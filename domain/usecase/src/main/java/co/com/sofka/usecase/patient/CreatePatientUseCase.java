package co.com.sofka.usecase.patient;

import co.com.sofka.model.generic.DomainEvent;
import co.com.sofka.model.patient.Patient;

import co.com.sofka.model.patient.values.Enable;
import co.com.sofka.model.patient.values.FullName;
import co.com.sofka.model.patient.values.PatientId;
import co.com.sofka.model.patient.values.TypeId;
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
                    new Enable("true"));
         patientRepository.createPatient(new CreatePatientCommand(command.getPatientId(),
                 command.getFullName(), command.getTypeId())).subscribe().isDisposed();
                        return patient.getUncommittedChanges();
        }).flatMap(event -> {
          return  repository.saveEvent(event);
            }).map(event -> {
            return event;
        });
    }
}
