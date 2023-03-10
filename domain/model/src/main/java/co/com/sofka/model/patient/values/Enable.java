package co.com.sofka.model.patient.values;

import co.com.sofka.model.generic.ValueObject;

public class Enable implements ValueObject<String> {

    private String enable;

    public Enable(String enable) {
        this.enable = enable;
    }

    @Override
    public String value() {
        return enable;
    }
}
