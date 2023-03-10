package co.com.sofka.model.agenda.events;

import co.com.sofka.model.generic.DomainEvent;

public class AgendaCreated extends DomainEvent {

    private String initialDate;
    private String endDate;

    public AgendaCreated(){super("ramirez.fernandez.agendacreated");}
    public AgendaCreated(String initialDate, String endDate) {
        super("ramirez.fernandez.agendacreated");
        this.initialDate = initialDate;
        this.endDate = endDate;
    }

    public String getInitialDate() {
        return initialDate;
    }

    public String getEndDate() {
        return endDate;
    }
}
