package co.com.sofka.usecase.patient.commands;

import co.com.sofka.model.patient.values.AppointmentId;
import co.com.sofka.usecase.generic.Command;

public class AssociateAppointmentCommand extends Command {

    private String patientId;
    private String appointmentDate;


    public AssociateAppointmentCommand() {
    }

    public AssociateAppointmentCommand(String patientId, String appointmentDate) {
        this.patientId = patientId;
        this.appointmentDate = appointmentDate;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

}
