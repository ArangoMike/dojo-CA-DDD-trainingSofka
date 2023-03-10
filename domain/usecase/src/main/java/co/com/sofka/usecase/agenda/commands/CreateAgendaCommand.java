package co.com.sofka.usecase.agenda.commands;

import co.com.sofka.usecase.generic.Command;

public class CreateAgendaCommand extends Command {

    private String agendaId;
    private String initialDate;
    private String endDate;

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
}
