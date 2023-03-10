package co.com.sofka.usecase.patient;

import co.com.sofka.model.events.gateways.EventBus;
import co.com.sofka.model.generic.DomainEvent;
import co.com.sofka.model.patient.Patient;
import co.com.sofka.model.patient.values.Email;
import co.com.sofka.model.patient.values.PatientId;
import co.com.sofka.usecase.gateways.DomainEventRepository;
import co.com.sofka.usecase.gateways.PatientRepository;
import co.com.sofka.usecase.generic.UseCaseForCommand;
import co.com.sofka.usecase.patient.commands.ModifyEmailPatientCommand;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ModifyEmailPatientUseCase extends UseCaseForCommand<ModifyEmailPatientCommand> {

    private final DomainEventRepository repository;
    private final PatientRepository patientRepository;

    private final EventBus bus;

    public ModifyEmailPatientUseCase(DomainEventRepository repository, PatientRepository patientRepository, EventBus bus) {
        this.repository = repository;
        this.patientRepository = patientRepository;
        this.bus = bus;
    }

    @Override
    public Flux<DomainEvent> apply(Mono<ModifyEmailPatientCommand> modifyEmailPatientCommandMono) {
        return modifyEmailPatientCommandMono.flatMapMany(command -> repository.findById(command.getPatientId())
                .collectList()
                .flatMapIterable(events ->{
                    Patient patient = Patient.from(PatientId.of(command.getPatientId()),events);

                    patient.ModifyEmailPatient(PatientId.of(command.getPatientId()),new Email(command.getEmail()));
                    patientRepository.modifyEmailPatient(new ModifyEmailPatientCommand(command.getPatientId(),
                            command.getEmail())).subscribe().isDisposed();
                    return patient.getUncommittedChanges();
                }).flatMap(event -> {
                    return repository.saveEvent(event);

                }).map(event ->{
                    bus.publish(event,command.getEmail());
                    return event;
                }));
    }
}
