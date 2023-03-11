package co.com.sofka.usecase.patient;

import co.com.sofka.model.events.gateways.EventBus;
import co.com.sofka.model.generic.DomainEvent;
import co.com.sofka.model.patient.Patient;
import co.com.sofka.model.patient.values.AppointmentId;
import co.com.sofka.model.patient.values.MedicalCheckup;
import co.com.sofka.model.patient.values.PatientId;
import co.com.sofka.usecase.gateways.DomainEventRepository;
import co.com.sofka.usecase.gateways.PatientRepository;
import co.com.sofka.usecase.generic.UseCaseForCommand;
import co.com.sofka.usecase.patient.commands.AssignMedicalCheckupAppointmentCommand;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



public class AssignMedicalCheckupAppointmentUseCase extends UseCaseForCommand<AssignMedicalCheckupAppointmentCommand> {

    private final DomainEventRepository repository;
    private final PatientRepository patientRepository;
    private final EventBus bus;

    public AssignMedicalCheckupAppointmentUseCase(DomainEventRepository repository, PatientRepository patientRepository, EventBus bus) {
        this.repository = repository;
        this.patientRepository = patientRepository;
        this.bus = bus;
    }


    @Override
    public Flux<DomainEvent> apply(Mono<AssignMedicalCheckupAppointmentCommand> assignMedicalCheckupAppointmentCommandMono) {
        return assignMedicalCheckupAppointmentCommandMono.flatMapMany(command -> repository.findById(command.getPatientId())
                .collectList()
                .flatMapIterable(events ->{
                    Patient patient = Patient.from(PatientId.of(command.getPatientId()), events);

                    patient.AssingMedicalCheckupAppointment(PatientId.of(command.getPatientId()),
                            AppointmentId.of(command.getAppointmentId()),new MedicalCheckup(command.getMedicalCheckup()));

                    patientRepository.assignMedicalCheckupByAppointmentById(
                            new AssignMedicalCheckupAppointmentCommand(command.getPatientId(),
                            command.getAppointmentId(), command.getMedicalCheckup())).subscribe().isDisposed();

                    return patient.getUncommittedChanges();
                }).flatMap(event -> {
                    return repository.saveEvent(event);
                }).map(event ->{
                    bus.publish(event, command.getAppointmentId());
                    return event;}));
    }
}
