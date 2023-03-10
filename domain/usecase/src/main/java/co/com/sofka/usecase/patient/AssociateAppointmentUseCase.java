package co.com.sofka.usecase.patient;

import co.com.sofka.model.generic.DomainEvent;
import co.com.sofka.model.patient.Patient;
import co.com.sofka.model.patient.values.AppointmentDate;
import co.com.sofka.model.patient.values.AppointmentId;
import co.com.sofka.model.patient.values.PatientId;
import co.com.sofka.usecase.gateways.PatientRepository;
import co.com.sofka.usecase.generic.UseCaseForCommand;
import co.com.sofka.usecase.patient.commands.AssociateAppointmentCommand;
import co.com.sofka.usecase.gateways.DomainEventRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class AssociateAppointmentUseCase extends UseCaseForCommand<AssociateAppointmentCommand> {

    private final DomainEventRepository repository;
    private final PatientRepository patientRepository;


    public AssociateAppointmentUseCase(DomainEventRepository repository, PatientRepository patientRepository) {
        this.repository = repository;
        this.patientRepository = patientRepository;
    }

    @Override
    public Flux<DomainEvent> apply(Mono<AssociateAppointmentCommand> associateAppointmentCommandMono) {
        return associateAppointmentCommandMono.flatMapMany(command -> repository.findById(command.getPatientId())
                .collectList()
                .flatMapIterable(events ->{
                    Patient patient = Patient.from(PatientId.of(command.getPatientId()), events);
                    var appointmentId = new AppointmentId();
                    patient.AssociateAppointment(new AppointmentDate(command.getAppointmentDate()),appointmentId);

                    patientRepository.addAppointmentPacient(new AssociateAppointmentCommand(appointmentId.value(),
                            command.getPatientId(), command.getAppointmentDate())).subscribe().isDisposed();

                    return patient.getUncommittedChanges();
                }).map(event -> {
                        return event;
                }).flatMap(event ->{
                        return repository.saveEvent(event);
                }));
    }
}
