package co.com.sofka.usecase.agenda.commands;

import co.com.sofka.usecase.generic.Command;

public class AssociatePatientCommand extends Command {

    private String AgendaId;
    private String patientId;

    public AssociatePatientCommand(){}

    public AssociatePatientCommand(String agendaId, String patientId) {
        AgendaId = agendaId;
        this.patientId = patientId;
    }

    public String getAgendaId() {
        return AgendaId;
    }

    public void setAgendaId(String agendaId) {
        AgendaId = agendaId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
}
