package co.com.sofka.usecase.gateways;

import co.com.sofka.usecase.agenda.commands.AssociateDayCommand;
import co.com.sofka.usecase.agenda.commands.CreateAgendaCommand;

import co.com.sofka.usecase.agenda.commands.DisableScheduleDayCommand;
import reactor.core.publisher.Mono;



public interface AgendaRepository {

    Mono<Void> createAgenda(CreateAgendaCommand createAgendaCommand);

    Mono<Void> addDayAgenda(AssociateDayCommand associateDayCommand);


    Mono<Void> assignSchedule(String schedule,String id) ;

    Mono<CreateAgendaCommand> getAgendaByid(String id);


    Mono<Void> associatePatientId(String patientId,String id);

    Mono<Void> disableScheduleDay(DisableScheduleDayCommand disableScheduleDayCommand);

}
