package co.com.sofka.model.patient.events;

import co.com.sofka.model.generic.DomainEvent;

public class EmailPatientModified extends DomainEvent {

  private String patientId;
    private String email;

    public EmailPatientModified(){
        super("ramirez.fernandez.emailpatientmodified");
    }

    public EmailPatientModified(String patientId, String email) {
        super("ramirez.fernandez.emailpatientmodified");
        this.patientId = patientId;
        this.email = email;
    }

    public String getPatientId() {return patientId;}

    public String getEmail() {return email;}

}
