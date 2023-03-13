package co.com.sofka.model.agenda.events;

import co.com.sofka.model.generic.DomainEvent;

public class PatientAssociated extends DomainEvent {

    private String agendaId;
    private String patientId;

    public PatientAssociated(){super("ramirez.fernandez.patientassociated");}

    public PatientAssociated( String agendaId, String patientId) {
        super("ramirez.fernandez.patientassociated");
        this.agendaId = agendaId;
        this.patientId = patientId;
    }

    public String getAgendaId() {
        return agendaId;
    }

    public String getPatientId() {
        return patientId;
    }
}

