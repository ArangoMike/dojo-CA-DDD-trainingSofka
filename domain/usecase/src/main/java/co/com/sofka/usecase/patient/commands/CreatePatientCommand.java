package co.com.sofka.usecase.patient.commands;

import co.com.sofka.usecase.generic.Command;

public class CreatePatientCommand extends Command {

    private String patientId;
    private String fullName;
    private String typeId;


    public CreatePatientCommand() {
    }

    public CreatePatientCommand(String patientId, String fullName, String typeId) {
        this.patientId = patientId;
        this.fullName = fullName;
        this.typeId = typeId;
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
