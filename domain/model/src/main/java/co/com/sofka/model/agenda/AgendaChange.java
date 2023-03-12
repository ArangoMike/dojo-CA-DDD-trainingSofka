package co.com.sofka.model.agenda;

import co.com.sofka.model.agenda.entities.Day;
import co.com.sofka.model.agenda.events.AgendaCreated;
import co.com.sofka.model.agenda.events.DayAssociated;
import co.com.sofka.model.agenda.events.DayScheduleDisabled;
import co.com.sofka.model.agenda.values.*;
import co.com.sofka.model.generic.EventChange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class AgendaChange extends EventChange {

    public AgendaChange(Agenda agenda){

        apply((AgendaCreated event)-> {
            agenda.initialDate = new InitialDate(event.getInitialDate());
            agenda.endDate = new EndDate(event.getEndDate());
            agenda.patients = new ArrayList<>();
            agenda.days = new ArrayList<>();
        });

        apply((DayAssociated event)-> {
            Day day = new Day(DayId.of(event.getDayId()),
                    new DayName(event.getDayName()),
                    (List<Schedule>) event.getSchedules());
            agenda.days.add(day);
        });


        apply((DayScheduleDisabled event)-> {

            Optional<Day> optionalDay =  agenda.days.stream()
                    .filter(day ->{

                        return day.DayName().equals(String.valueOf(event.getDayName()));
                    })
                    .findFirst();
            if (optionalDay.isPresent()) {

                Day day =optionalDay.get();
                // Buscamos el horario con schedule "08:00"
                Optional<Schedule> optionalSchedule = day.Schedules().stream()
                        .filter(schedule1 -> schedule1.value().schedule()
                                .contains(String.valueOf(event.getSchedule())))
                        .findFirst();

                if (optionalSchedule.isPresent()) {

                    Schedule schedule2 = optionalSchedule.get();

                    // Modificamos el atributo "enable" del horario a false
                    if(!schedule2.value().enable()) {

                        agenda.days.remove(day);
                        day.Schedules().remove(schedule2);

                        Schedule newSchedule = new Schedule(schedule2.value().schedule(),event.getEnable());
                        day.Schedules().add(newSchedule);
                        agenda.days.add(day);

                    }

                }
            }

        });


    }


}
