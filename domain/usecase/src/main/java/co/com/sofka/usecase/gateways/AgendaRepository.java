package co.com.sofka.usecase.gateways;

import co.com.sofka.usecase.agenda.commands.AssociateDayCommand;
import co.com.sofka.usecase.agenda.commands.CreateAgendaCommand;

import reactor.core.publisher.Mono;



public interface AgendaRepository {

    Mono<CreateAgendaCommand> createAgenda(CreateAgendaCommand createAgendaCommand);

    Mono<CreateAgendaCommand> addDayAgenda(AssociateDayCommand associateDayCommand);


    Mono<Boolean> assignSchedule(String schedule,String id) ;

    Mono<CreateAgendaCommand> getAgendaByid(String id);

}
