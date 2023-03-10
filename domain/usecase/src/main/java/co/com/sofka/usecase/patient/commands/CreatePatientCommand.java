package co.com.sofka.usecase.patient.commands;

import co.com.sofka.model.patient.events.AppointmentAssociated;
import co.com.sofka.usecase.generic.Command;

import java.util.List;

public class CreatePatientCommand extends Command {

    private String patientId;
    private String enable;
    private String fullName;
    private String typeId;
    private List<Object> appointments;


    public CreatePatientCommand() {
    }

    public CreatePatientCommand(String patientId, String fullName, String typeId) {
        this.patientId = patientId;
        this.enable = "true";
        this.fullName = fullName;
        this.typeId = typeId;
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
