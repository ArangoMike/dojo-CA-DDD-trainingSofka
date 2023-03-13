package co.com.sofka.usecase.patient;

import co.com.sofka.model.agenda.events.AgendaDayScheduleAssigned;
import co.com.sofka.model.events.gateways.EventBus;
import co.com.sofka.model.generic.DomainEvent;
import co.com.sofka.model.patient.Patient;
import co.com.sofka.model.patient.values.AppointmentDate;
import co.com.sofka.model.patient.values.AppointmentId;
import co.com.sofka.model.patient.values.PatientId;
import co.com.sofka.usecase.gateways.DomainEventRepository;
import co.com.sofka.usecase.gateways.PatientRepository;
import co.com.sofka.usecase.patient.commands.AssociateAppointmentCommand;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class AssociateAppointmentEventUseCase implements Function<Mono<AgendaDayScheduleAssigned>, Flux<DomainEvent>> {

    private final DomainEventRepository repository;
    private final PatientRepository patientRepository;
    private final EventBus bus;

    public AssociateAppointmentEventUseCase(DomainEventRepository repository, PatientRepository patientRepository, EventBus bus) {
        this.repository = repository;
        this.patientRepository = patientRepository;
        this.bus = bus;
    }

    @Override
    public Flux<DomainEvent> apply(Mono<AgendaDayScheduleAssigned> agendaDayScheduleAssignedMono) {
        return agendaDayScheduleAssignedMono.flatMapMany(command -> repository.findById(command.getPatientId())
                .collectList()
                .flatMapIterable(events -> {
                    Patient patient = Patient.from(PatientId.of(command.getPatientId()), events);

                    var appointmentId = new AppointmentId();

                    patient.AssociateAppointment(new AppointmentDate(command.getAppointmentDate()), appointmentId);

                    patientRepository.addAppointmentPatient(new AssociateAppointmentCommand(appointmentId.value(),
                            command.getPatientId(), command.getAppointmentDate())).subscribe().isDisposed();

                    return patient.getUncommittedChanges();
                }).flatMap(event -> {
                    return repository.saveEvent(event);
                }).map(event -> {
                    String email = String.valueOf(patientRepository.findEmailById(command.getPatientId()));
                    bus.publish(event, email);
                    return event;
                }));
    }
}

