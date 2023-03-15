package co.com.sofka.model.agenda;


import co.com.sofka.model.agenda.entities.Day;
import co.com.sofka.model.agenda.events.*;
import co.com.sofka.model.agenda.values.*;
import co.com.sofka.model.generic.AggregateRoot;
import co.com.sofka.model.generic.DomainEvent;
import co.com.sofka.model.patient.values.AppointmentDate;
import co.com.sofka.model.patient.values.PatientId;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
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

    public void AssignScheduleDayAgenda(AgendaId agendaId,PatientId patientId, AppointmentDate appointmentDate){

        Objects.requireNonNull(patientId);
        Objects.requireNonNull(agendaId);
        Objects.requireNonNull(appointmentDate);
        appendChange(new AgendaDayScheduleAssigned(agendaId.value(),patientId.value(),appointmentDate.value()));

    }

    public void DisableScheduleDay(PatientId patientId,AgendaId agendaId,
                                   DayName dayName,String schedule,Boolean enable){
        Objects.requireNonNull(patientId);
        Objects.requireNonNull(agendaId);
        Objects.requireNonNull(dayName);
        Objects.requireNonNull(schedule);
        appendChange(new DayScheduleDisabled(agendaId.value(), patientId.value(), dayName.value(),schedule,enable));
    }

    public void AssociatePatient(AgendaId agendaId,PatientId patientId){
        Objects.requireNonNull(patientId);
        Objects.requireNonNull(agendaId);
        appendChange(new PatientAssociated(agendaId.value(),patientId.value()));
    }


    public static LocalDateTime dateformat(String dateString) throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date date = dateFormat.parse(dateString);
        Instant instant = date.toInstant();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return localDateTime;
    }

}
