package co.com.sofka.model.patient.events;

import co.com.sofka.model.generic.DomainEvent;

public class MedicalCheckupAppointmentAssigned extends DomainEvent {

    private String patientId;
    private String medicalCheckup;
    private String appointmentId;


    public MedicalCheckupAppointmentAssigned() {
        super("ramirez.fernandez.medicalcheckupappointmentassigned");
    }

    public MedicalCheckupAppointmentAssigned(String patientId, String appointmentId, String medicalCheckup) {
        super("ramirez.fernandez.medicalcheckupappointmentassigned");
        this.patientId = patientId;
        this.medicalCheckup = medicalCheckup;
        this. appointmentId = appointmentId;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getMedicalCheckup() {
        return medicalCheckup;
    }

    public String getAppointmentId() {
        return appointmentId;
    }
}
