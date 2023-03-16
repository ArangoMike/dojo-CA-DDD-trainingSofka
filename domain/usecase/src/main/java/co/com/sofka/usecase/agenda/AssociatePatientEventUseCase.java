package co.com.sofka.usecase.agenda;

import co.com.sofka.model.agenda.Agenda;
import co.com.sofka.model.agenda.events.AgendaDayScheduleAssigned;
import co.com.sofka.model.agenda.values.AgendaId;
import co.com.sofka.model.events.gateways.EventBus;
import co.com.sofka.model.generic.DomainEvent;

import co.com.sofka.model.patient.values.PatientId;
import co.com.sofka.usecase.gateways.AgendaRepository;
import co.com.sofka.usecase.gateways.DomainEventRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class AssociatePatientEventUseCase implements Function<Mono<AgendaDayScheduleAssigned>, Flux<DomainEvent>> {

    private final DomainEventRepository repository;
    private final AgendaRepository agendaRepository;

    private final EventBus bus;


    public AssociatePatientEventUseCase(DomainEventRepository repository, AgendaRepository agendaRepository, EventBus bus) {
        this.repository = repository;
        this.agendaRepository = agendaRepository;
        this.bus = bus;
    }

    @Override
    public Flux<DomainEvent> apply(Mono<AgendaDayScheduleAssigned> agendaDayScheduleAssignedMono) {
        return agendaDayScheduleAssignedMono.flatMapMany(command -> repository.findById(command.getAgendaId())
                .collectList()
                .flatMapIterable(events -> {
                    Agenda agenda = Agenda.from(AgendaId.of(command.getAgendaId()), events);

                    agenda.AssociatePatient(AgendaId.of(command.getAgendaId()),PatientId.of(command.getPatientId()));

                    agendaRepository.associatePatientId(command.getPatientId(), command.getAgendaId()).subscribe();

                    return agenda.getUncommittedChanges();
                }).flatMap(event -> {
                    return repository.saveEvent(event);
                }).map(event -> {

                    bus.publish(event, event.type);
                    return event;
                }));
    }
}
