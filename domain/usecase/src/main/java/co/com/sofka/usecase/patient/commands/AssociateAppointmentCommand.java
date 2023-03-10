package co.com.sofka.usecase.patient.commands;

import co.com.sofka.usecase.generic.Command;

public class AssociateAppointmentCommand extends Command {


  private String appointmentId;
    private String patientId;
    private String appointmentDate;


    public AssociateAppointmentCommand() {
    }

    public AssociateAppointmentCommand(String appointmentId,String patientId, String appointmentDate) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
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
