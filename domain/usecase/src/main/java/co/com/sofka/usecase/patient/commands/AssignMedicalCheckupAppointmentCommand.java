package co.com.sofka.usecase.patient.commands;

import co.com.sofka.usecase.generic.Command;

public class AssignMedicalCheckupAppointmentCommand extends Command {

    private String patientId;
    private String appointmentId;
    private String medicalCheckup;

    public AssignMedicalCheckupAppointmentCommand() {
    }

    public AssignMedicalCheckupAppointmentCommand(String patientId, String appointmentId, String medicalCheckup) {
        this.patientId = patientId;
        this.appointmentId = appointmentId;
        this.medicalCheckup = medicalCheckup;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getMedicalCheckup() {
        return medicalCheckup;
    }

    public void setMedicalCheckup(String medicalCheckup) {
        this.medicalCheckup = medicalCheckup;
    }
}
