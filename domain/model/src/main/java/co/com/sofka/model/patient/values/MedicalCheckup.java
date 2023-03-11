package co.com.sofka.model.patient.values;

import co.com.sofka.model.generic.ValueObject;

public class MedicalCheckup implements ValueObject<String> {

    private String medicalCheckup;

    public MedicalCheckup(String medicalCheckup) {
        this.medicalCheckup = medicalCheckup;
    }

    @Override
    public String value() {
        return medicalCheckup;
    }
}
