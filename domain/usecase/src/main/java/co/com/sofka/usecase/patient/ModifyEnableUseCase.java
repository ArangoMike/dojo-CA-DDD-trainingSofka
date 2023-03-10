package co.com.sofka.usecase.patient;

import co.com.sofka.model.generic.DomainEvent;
import co.com.sofka.model.patient.Patient;
import co.com.sofka.model.patient.values.Enable;
import co.com.sofka.model.patient.values.PatientId;
import co.com.sofka.usecase.gateways.DomainEventRepository;
import co.com.sofka.usecase.gateways.PatientRepository;
import co.com.sofka.usecase.generic.UseCaseForCommand;
import co.com.sofka.usecase.patient.commands.ModifyEnableCommand;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ModifyEnableUseCase extends UseCaseForCommand<ModifyEnableCommand> {

    private final DomainEventRepository repository;

    private final PatientRepository patientRepository;

    public ModifyEnableUseCase(DomainEventRepository repository, PatientRepository patientRepository) {
        this.repository = repository;
        this.patientRepository = patientRepository;
    }

    @Override
    public Flux<DomainEvent> apply(Mono<ModifyEnableCommand> modifyEnableCommandMono) {
        return modifyEnableCommandMono.flatMapMany(command -> repository.findById(command.getPatientId())
                .collectList()
                .flatMapIterable(events ->{
                    Patient patient = Patient.from(PatientId.of(command.getPatientId()),events);

                    patient.ModifyEnable(PatientId.of(command.getPatientId()),new Enable(command.getEnable()));
                    patientRepository.updateEnablePacient(new ModifyEnableCommand(command.getPatientId(),
                            command.getEnable())).subscribe().isDisposed();
                    return patient.getUncommittedChanges();
                }).map(event -> {
                    return event;
                }).flatMap(event ->{
                    return repository.saveEvent(event);
                }));
    }
}
