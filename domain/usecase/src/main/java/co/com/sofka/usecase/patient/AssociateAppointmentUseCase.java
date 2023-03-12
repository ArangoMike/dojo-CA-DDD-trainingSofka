package co.com.sofka.usecase.patient;

import co.com.sofka.model.events.gateways.EventBus;
import co.com.sofka.model.generic.DomainEvent;
import co.com.sofka.model.patient.Patient;
import co.com.sofka.model.patient.values.AppointmentDate;
import co.com.sofka.model.patient.values.AppointmentId;
import co.com.sofka.model.patient.values.PatientId;
import co.com.sofka.usecase.gateways.AgendaRepository;
import co.com.sofka.usecase.gateways.PatientRepository;
import co.com.sofka.usecase.generic.UseCaseForCommand;
import co.com.sofka.usecase.patient.commands.AssociateAppointmentCommand;
import co.com.sofka.usecase.gateways.DomainEventRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.ParseException;


public class AssociateAppointmentUseCase extends UseCaseForCommand<AssociateAppointmentCommand> {

    private final DomainEventRepository repository;
    private final PatientRepository patientRepository;
    private final AgendaRepository agendaRepository;

    private final EventBus bus;


    public AssociateAppointmentUseCase(DomainEventRepository repository, PatientRepository patientRepository, AgendaRepository agendaRepository, EventBus bus) {
        this.repository = repository;
        this.patientRepository = patientRepository;
        this.agendaRepository = agendaRepository;
        this.bus = bus;
    }

    @Override
    public Flux<DomainEvent> apply(Mono<AssociateAppointmentCommand> associateAppointmentCommandMono) {
       return   associateAppointmentCommandMono.flatMapMany(command -> repository.findById(command.getPatientId())
                .collectList()
                .flatMapIterable(events ->{
                    Patient patient = Patient.from(PatientId.of(command.getPatientId()), events);

                    var appointmentId = new AppointmentId();

                    patient.AssociateAppointment(new AppointmentDate(command.getAppointmentDate()),appointmentId);

                    patientRepository.addAppointmentPatient(new AssociateAppointmentCommand(appointmentId.value(),
                            command.getPatientId(), command.getAppointmentDate())).subscribe().isDisposed();

                    return patient.getUncommittedChanges();
                }).flatMap(event -> {
                   return repository.saveEvent(event);
                }).map(event ->{String email = String.valueOf(patientRepository.findEmailById(command.getPatientId()));
                    bus.publish(event, email);
                    return event;
                }));
    }


    public Flux<DomainEvent> validationsAppointment(Mono<AssociateAppointmentCommand> associateAppointmentCommand,String agendaid){

        Mono<Boolean> validation = associateAppointmentCommand.flatMap(command ->{
                 return patientRepository.findById(command.getPatientId()).map(patientRes -> {
                     Mono<Boolean> res = null;
                     if (patientRes.getEnable().equals("true")) {

                        res =  agendaRepository.assignSchedule(command.getAppointmentDate(), agendaid);
                        return res;
                     }else { return res = Mono.just(false); }
                 });
         }).flatMap(resF ->{ return resF;});

        return validation
                .filter(validation1 -> validation1)
                .flatMapMany(validation1 -> apply(associateAppointmentCommand));

    }



}
