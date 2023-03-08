package co.com.sofka.model.patient.events;

import co.com.sofka.model.generic.DomainEvent;

public class MedicalHistoryofAppointmentAdded extends DomainEvent {

    private String patientId;
    private String medicalHistory;
    private String appointmentId;
    public MedicalHistoryofAppointmentAdded(String patientId, String appointmentId, String medicalHistory) {
        super("ramirez.fernandez.medicalhistoryofappointmentadded");
        this.patientId = patientId;
        this.medicalHistory = medicalHistory;
        this. appointmentId = appointmentId;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public String getAppointmentId() {
        return appointmentId;
    }
}
