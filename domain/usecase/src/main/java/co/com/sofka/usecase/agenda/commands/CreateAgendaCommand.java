package co.com.sofka.usecase.agenda.commands;

import co.com.sofka.model.agenda.entities.Day;
import co.com.sofka.model.patient.values.PatientId;
import co.com.sofka.usecase.generic.Command;

import java.util.List;

public class CreateAgendaCommand extends Command {

    private String agendaId;
    private String initialDate;
    private String endDate;
    protected List<String> patients;
    protected List<Object> days;

    public CreateAgendaCommand(){}

    public CreateAgendaCommand(String agendaId, String initialDate, String endDate) {
        this.agendaId = agendaId;
        this.initialDate = initialDate;
        this.endDate = endDate;
    }

    public String getAgendaId() {
        return agendaId;
    }

    public void setAgendaId(String agendaId) {
        this.agendaId = agendaId;
    }

    public String getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(String initialDate) {
        this.initialDate = initialDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<String> getPatients() {
        return patients;
    }

    public void setPatients(List<String> patients) {
        this.patients = patients;
    }

    public List<Object> getDays() {
        return days;
    }

    public void setDays(List<Object> days) {
        this.days = days;
    }
}
