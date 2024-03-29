package co.com.sofka.usecase.agenda;

import co.com.sofka.model.agenda.Agenda;
import co.com.sofka.model.agenda.values.AgendaId;
import co.com.sofka.model.agenda.values.EndDate;
import co.com.sofka.model.agenda.values.InitialDate;
import co.com.sofka.model.generic.DomainEvent;
import co.com.sofka.usecase.agenda.commands.CreateAgendaCommand;
import co.com.sofka.usecase.gateways.AgendaRepository;
import co.com.sofka.usecase.gateways.DomainEventRepository;
import co.com.sofka.usecase.generic.UseCaseForCommand;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public class CreateAgendaUseCase extends UseCaseForCommand<CreateAgendaCommand> {

    private final DomainEventRepository repository;

    private final AgendaRepository agendaRepository;


    public CreateAgendaUseCase(DomainEventRepository repository, AgendaRepository agendaRepository) {
        this.repository = repository;
        this.agendaRepository = agendaRepository;
    }

    @Override
    public Flux<DomainEvent> apply(Mono<CreateAgendaCommand> createAgendaCommandMono) {
        return createAgendaCommandMono.flatMapIterable(command ->{
            Agenda agenda = new Agenda(AgendaId.of(command.getAgendaId()),
                    new InitialDate(command.getInitialDate()),
                    new EndDate(command.getEndDate()));
            agendaRepository.createAgenda(new CreateAgendaCommand(command.getAgendaId(),
                    command.getInitialDate(), command.getEndDate())).subscribe();
            return agenda.getUncommittedChanges();
        }).flatMap(event -> {
            if (event == null) {
                return Mono.empty();
            }
            return repository.saveEvent(event);
        }).map(event -> {
            return event;
        });
    }
}
