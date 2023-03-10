package co.com.sofka.usecase.patient.commands;

import co.com.sofka.usecase.generic.Command;

public class ModifyEnableCommand extends Command {

    private String patientId;
    private String enable;

    public ModifyEnableCommand(){}

    public ModifyEnableCommand(String patientId, String enable) {
        this.patientId = patientId;
        this.enable = enable;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }
}
