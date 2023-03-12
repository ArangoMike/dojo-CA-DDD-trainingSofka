package co.com.sofka.model.agenda;


import co.com.sofka.model.agenda.entities.Day;
import co.com.sofka.model.agenda.events.AgendaCreated;
import co.com.sofka.model.agenda.events.DayAssociated;
import co.com.sofka.model.agenda.values.*;
import co.com.sofka.model.generic.AggregateRoot;
import co.com.sofka.model.generic.DomainEvent;

import co.com.sofka.model.patient.values.PatientId;

import java.util.List;
import java.util.Objects;

public class Agenda extends AggregateRoot<AgendaId> {

    protected InitialDate initialDate;
    protected EndDate endDate;
    protected List<PatientId> patients;
    protected List<Day> days;

    public Agenda(AgendaId entityId, InitialDate initialDate, EndDate endDate) {
        super(entityId);
        subscribe(new AgendaChange(this));
        appendChange(new AgendaCreated(initialDate.value(),endDate.value())).apply();
    }

    public Agenda(AgendaId id) {
        super(id);
    }

    public static Agenda from(AgendaId id, List<DomainEvent> events){
        Agenda agenda = new Agenda(id);
        events.forEach(event -> agenda.applyEvent(event));
        return agenda;
    }

    public void AssociateDay(DayName dayName, List<Schedule> schedules,DayId dayId){

        Objects.requireNonNull(dayName);
        Objects.requireNonNull(schedules);
        appendChange(new DayAssociated(dayId.value(),dayName.value(),schedules)).apply();
    }

}
