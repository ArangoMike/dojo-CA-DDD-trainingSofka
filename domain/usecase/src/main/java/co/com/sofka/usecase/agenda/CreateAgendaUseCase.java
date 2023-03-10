package co.com.sofka.usecase.agenda;

import co.com.sofka.model.agenda.Agenda;
import co.com.sofka.model.agenda.values.AgendaId;
import co.com.sofka.model.agenda.values.EndDate;
import co.com.sofka.model.agenda.values.InitialDate;
import co.com.sofka.model.generic.DomainEvent;
import co.com.sofka.usecase.agenda.commands.CreateAgendaCommand;
import co.com.sofka.usecase.gateways.DomainEventRepository;
import co.com.sofka.usecase.generic.UseCaseForCommand;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public class CreateAgendaUseCase extends UseCaseForCommand<CreateAgendaCommand> {

    private final DomainEventRepository repository;

    public CreateAgendaUseCase(DomainEventRepository repository) {
        this.repository = repository;
    }

    @Override
    public Flux<DomainEvent> apply(Mono<CreateAgendaCommand> createAgendaCommandMono) {
        return createAgendaCommandMono.flatMapIterable(command ->{
            Agenda agenda = new Agenda(AgendaId.of(command.getAgendaId()),
                    new InitialDate(command.getInitialDate()),
                    new EndDate(command.getEndDate()));
            return agenda.getUncommittedChanges();
        }).flatMap(event -> {
            return  repository.saveEvent(event);
        }).map(event -> {
            return event;
        });
    }
}
