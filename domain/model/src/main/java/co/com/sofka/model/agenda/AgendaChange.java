package co.com.sofka.model.agenda;

import co.com.sofka.model.agenda.entities.Day;
import co.com.sofka.model.agenda.events.AgendaCreated;
import co.com.sofka.model.agenda.events.DayAssociated;
import co.com.sofka.model.agenda.values.*;
import co.com.sofka.model.generic.EventChange;

import java.util.ArrayList;
import java.util.List;


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

    }


}
