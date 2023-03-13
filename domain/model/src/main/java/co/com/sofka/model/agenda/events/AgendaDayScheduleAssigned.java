package co.com.sofka.model.agenda.events;


import co.com.sofka.model.generic.DomainEvent;


public class AgendaDayScheduleAssigned extends DomainEvent {

    private String agendaId;

    private String patientId;
    private String appointmentDate;

    public AgendaDayScheduleAssigned(){super("ramirez.fernandez.agendadayscheduleAssigned");}

    public AgendaDayScheduleAssigned(String agendaId, String patientId, String appointmentDate) {
        super("ramirez.fernandez.agendadayscheduleAssigned");
        this.agendaId = agendaId;

        this.patientId = patientId;
        this.appointmentDate = appointmentDate;
    }

    public String getAgendaId() {
        return agendaId;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }
}
