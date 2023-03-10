package co.com.sofka.usecase.patient.commands;


import co.com.sofka.usecase.generic.Command;

import java.util.List;

public class CreatePatientCommand extends Command {

    private String patientId;
    private String enable;
    private String fullName;
    private String typeId;
    private String email;
    private List<Object> appointments;


    public CreatePatientCommand() {
    }

    public CreatePatientCommand(String patientId, String fullName, String typeId,String email) {
        this.patientId = patientId;
        this.enable = "true";
        this.fullName = fullName;
        this.typeId = typeId;
        this.email = email;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Object> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Object> appointments) {
        this.appointments = appointments;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }
}
