package co.com.sofka.usecase.agenda;

import co.com.sofka.model.agenda.Agenda;
import co.com.sofka.model.agenda.values.AgendaId;

import co.com.sofka.model.events.gateways.EventBus;
import co.com.sofka.model.generic.DomainEvent;
import co.com.sofka.model.patient.values.AppointmentDate;
import co.com.sofka.model.patient.values.PatientId;
import co.com.sofka.usecase.agenda.commands.AssignScheduleDayAgendaCommand;
import co.com.sofka.usecase.gateways.AgendaRepository;
import co.com.sofka.usecase.gateways.DomainEventRepository;
import co.com.sofka.usecase.gateways.PatientRepository;
import co.com.sofka.usecase.generic.UseCaseForCommand;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public class AssignScheduleDayAgendaUseCase extends UseCaseForCommand<AssignScheduleDayAgendaCommand> {

    private final DomainEventRepository repository;
    private final AgendaRepository agendaRepository;
    private final PatientRepository patientRepository;

    private final EventBus bus;

    public AssignScheduleDayAgendaUseCase(DomainEventRepository repository, AgendaRepository agendaRepository, PatientRepository patientRepository, EventBus bus) {
        this.repository = repository;
        this.agendaRepository = agendaRepository;
        this.patientRepository = patientRepository;
        this.bus = bus;
    }

    @Override
    public Flux<DomainEvent> apply(Mono<AssignScheduleDayAgendaCommand> assignScheduleDayAgendaCommandMono) {

        return assignScheduleDayAgendaCommandMono.flatMapMany(command ->
                repository.findById(command.getAgendaId())
                        .collectList()
                        .flatMapMany(events -> {
                            Agenda agenda = Agenda.from(AgendaId.of(command.getAgendaId()), events);
                            return patientRepository.findById(command.getPatientId())
                                    .flatMapMany(patientRes -> {
                                        if (patientRes.getEnable().equals("true")) {
                                            agenda.AssignScheduleDayAgenda(AgendaId.of(command.getAgendaId()),
                                                    PatientId.of(command.getPatientId()),
                                                    new AppointmentDate(command.getAppointmentDate()));
                                            agendaRepository.assignSchedule(command.getAppointmentDate(), command.getAgendaId()).subscribe();
                                        }
                                        return Flux.fromIterable(agenda.getUncommittedChanges());
                                    });
                        })
                        .flatMap(event -> repository.saveEvent(event))
        ).map(event -> {
            bus.publish(event,event.type);
            return event;
        });


    }
}





