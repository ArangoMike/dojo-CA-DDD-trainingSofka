package co.com.sofka.model.patient.events;

import co.com.sofka.model.generic.DomainEvent;

public class PatientCreated extends DomainEvent {

    private String fullName;
    private String typeId;

    public PatientCreated(){
        super("ramirez.hernandez.patientcreated");
    }

    public PatientCreated( String fullName,String typeId) {
        super("ramirez.fernandez.patientcreated");
        this.fullName = fullName;
        this.typeId = typeId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getTypeId() {
        return typeId;
    }
}
