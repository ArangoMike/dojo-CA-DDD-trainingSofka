package co.com.sofka.model.patient.values;

import co.com.sofka.model.generic.ValueObject;

public class MedicalHistory implements ValueObject<String> {

    private String medicalHistory;

    public MedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    @Override
    public String value() {
        return medicalHistory;
    }
}
