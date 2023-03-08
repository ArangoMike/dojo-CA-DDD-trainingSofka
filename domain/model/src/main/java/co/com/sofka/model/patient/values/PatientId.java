package co.com.sofka.model.patient.values;

import co.com.sofka.model.generic.Identity;

public class PatientId extends Identity {

    private PatientId(String uuid) {
        super(uuid);
    }

    public PatientId() {
    }

    public static PatientId of(String uuid){
        return new PatientId(uuid);
    }
}
