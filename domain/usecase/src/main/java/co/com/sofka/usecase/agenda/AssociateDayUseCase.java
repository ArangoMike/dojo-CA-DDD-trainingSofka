package co.com.sofka.usecase.agenda;

import co.com.sofka.model.agenda.Agenda;
import co.com.sofka.model.agenda.values.AgendaId;
import co.com.sofka.model.agenda.values.DayId;
import co.com.sofka.model.agenda.values.DayName;
import co.com.sofka.model.agenda.values.Schedule;
import co.com.sofka.model.generic.DomainEvent;
import co.com.sofka.usecase.agenda.commands.AssociateDayCommand;
import co.com.sofka.usecase.gateways.AgendaRepository;
import co.com.sofka.usecase.gateways.DomainEventRepository;
import co.com.sofka.usecase.generic.UseCaseForCommand;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class AssociateDayUseCase extends UseCaseForCommand<AssociateDayCommand> {

    private final DomainEventRepository repository;
    private final AgendaRepository agendaRepository;

    public AssociateDayUseCase(DomainEventRepository repository, AgendaRepository agendaRepository) {
        this.repository = repository;
        this.agendaRepository = agendaRepository;
    }

    @Override
    public Flux<DomainEvent> apply(Mono<AssociateDayCommand> associateDayCommandMono) {
        return associateDayCommandMono.flatMapMany(command -> repository.findById(command.getAgendaId())
                .collectList()
                .flatMapIterable(events ->{
                    Agenda agenda = Agenda.from(AgendaId.of(command.getAgendaId()), events);

                    var dayId = new DayId();
                    agenda.AssociateDay(new DayName(command.getDayName()),(List<Schedule>)command.getSchedules(),dayId);

                    agendaRepository.addDayAgenda(new AssociateDayCommand(dayId.value(),
                            command.getAgendaId(), command.getDayName(), command.getSchedules())).subscribe().isDisposed();

                    return agenda.getUncommittedChanges();
                }).map(event -> {
                    return event;
                }).flatMap(event ->{
                    return repository.saveEvent(event);
                }));
    }
}
