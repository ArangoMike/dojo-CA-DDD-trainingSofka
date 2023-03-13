package co.com.sofka.model.agenda.events;


import co.com.sofka.model.generic.DomainEvent;

public class DayScheduleDisabled extends DomainEvent {

    private String agendaId;
    private String patientId;
    private String dayName;
    private String schedule;
    private Boolean enable;

    public DayScheduleDisabled(){super("ramirez.fernandez.dayscheduledisabled");}

    public DayScheduleDisabled( String agendaId, String patientId, String dayName, String schedule, Boolean enable) {
        super("ramirez.fernandez.dayscheduledisabled");
        this.agendaId = agendaId;
        this.patientId = patientId;
        this.dayName = dayName;
        this.schedule = schedule;
        this.enable = enable;
    }

    public String getAgendaId() {
        return agendaId;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getDayName() {
        return dayName;
    }

    public String getSchedule() {
        return schedule;
    }

    public Boolean getEnable() {
        return enable;
    }
}
