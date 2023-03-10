package co.com.sofka.model.patient.entities;

import co.com.sofka.model.generic.Entity;
import co.com.sofka.model.patient.values.*;

public class Appointment extends Entity<AppointmentId> {

    private MedicalHistory medicalHistory;
    private AppointmentDate appointmentDate;


    public Appointment(AppointmentId entityId, AppointmentDate appointmentDate) {
        super(entityId);
        this.appointmentDate = appointmentDate;
    }

    public MedicalHistory MedicalHistory() {
        return medicalHistory;
    }

    public AppointmentDate AppointmentDate() {
        return appointmentDate;
    }
}
