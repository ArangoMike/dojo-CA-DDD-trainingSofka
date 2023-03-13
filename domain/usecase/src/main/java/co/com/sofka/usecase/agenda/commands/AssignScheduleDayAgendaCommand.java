package co.com.sofka.usecase.agenda.commands;

import co.com.sofka.usecase.generic.Command;

public class AssignScheduleDayAgendaCommand extends Command {

    private String agendaId;
    private String patientId;
    private String appointmentDate;

    public AssignScheduleDayAgendaCommand() {
    }

    public AssignScheduleDayAgendaCommand(String agendaId, String patientId, String appointmentDate) {
        this.agendaId = agendaId;
        this.patientId = patientId;
        this.appointmentDate = appointmentDate;
    }

    public String getAgendaId() {
        return agendaId;
    }

    public void setAgendaId(String agendaId) {
        this.agendaId = agendaId;
    }



    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }
}
