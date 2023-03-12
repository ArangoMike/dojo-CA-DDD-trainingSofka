package co.com.sofka.model.patient.entities;

import co.com.sofka.model.generic.Entity;
import co.com.sofka.model.patient.values.*;

public class Appointment extends Entity<AppointmentId> {

    private MedicalCheckup medicalCheckup;
    private AppointmentDate appointmentDate;


    public Appointment(AppointmentId entityId, AppointmentDate appointmentDate) {
        super(entityId);
        this.appointmentDate = appointmentDate;
    }

    public void assignMedicalCheckup(String medicalCheckup){
        this.medicalCheckup = new MedicalCheckup(medicalCheckup);
    }

    public MedicalCheckup medicalCheckup() {
        return medicalCheckup;
    }

    public AppointmentDate AppointmentDate() {
        return appointmentDate;
    }
}
