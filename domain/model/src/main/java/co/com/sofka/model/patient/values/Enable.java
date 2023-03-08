package co.com.sofka.model.patient.values;

import co.com.sofka.model.generic.ValueObject;

public class Enable implements ValueObject<Boolean> {

    private Boolean enable;

    public Enable(Boolean enable) {
        this.enable = enable;
    }

    @Override
    public Boolean value() {
        return enable;
    }
}
