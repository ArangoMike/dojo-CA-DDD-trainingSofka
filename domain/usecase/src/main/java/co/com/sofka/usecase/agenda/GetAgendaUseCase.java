package co.com.sofka.usecase.agenda;

import co.com.sofka.model.agenda.events.AgendaCreated;
import co.com.sofka.usecase.agenda.commands.CreateAgendaCommand;
import co.com.sofka.usecase.gateways.AgendaRepository;
import reactor.core.publisher.Mono;

public class GetAgendaUseCase {

    private final AgendaRepository agendaRepository;

    public GetAgendaUseCase(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }

    public Mono<CreateAgendaCommand> apply(String id){
        return agendaRepository.getAgendaByid(id);
    }
}
