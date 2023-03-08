package co.com.sofka.model.patient.events;

import co.com.sofka.model.generic.DomainEvent;

public class EnableModified extends DomainEvent {

    private String patientId;
    private Boolean enable;
    public EnableModified() {
        super("ramirez.fernandez.enableModified");
    }

    public EnableModified(String patientId, Boolean enable) {
        super("ramirez.fernandez.enableModified");
        this.patientId = patientId;
        this.enable = enable;
    }

    public String getPatientId() {
        return patientId;
    }

    public Boolean getEnable() {
        return enable;
    }
}
