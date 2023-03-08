package co.com.sofka.model.patient.events;

import co.com.sofka.model.generic.DomainEvent;

public class PatientCreated extends DomainEvent {

    private String fullName;

    public PatientCreated(){
        super("ramirez.hernandez.patientcreated");
    }

    public PatientCreated( String fullName) {
        super("ramirez.fernandez.patientcreated");
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }
}
