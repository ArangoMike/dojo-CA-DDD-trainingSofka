package co.com.sofka.usecase.patient.commands;

import co.com.sofka.usecase.generic.Command;


public class ModifyEmailPatientCommand extends Command {

    private String patientId;

    private String email;



    public ModifyEmailPatientCommand() {
    }


    public ModifyEmailPatientCommand(String patientId, String email) {
        this.patientId = patientId;
        this.email = email;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
