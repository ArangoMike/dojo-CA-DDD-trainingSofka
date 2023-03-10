package co.com.sofka.model.patient.events;

import co.com.sofka.model.generic.DomainEvent;

public class EnablePatientModified extends DomainEvent {

    private String patientId;
    private String enable;
    public EnablePatientModified() {
        super("ramirez.fernandez.enablepatientmodified");
    }

    public EnablePatientModified(String patientId, String enable) {
        super("ramirez.fernandez.enablepatientmodified");
        this.patientId = patientId;
        this.enable = enable;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getEnable() {
        return enable;
    }
}
