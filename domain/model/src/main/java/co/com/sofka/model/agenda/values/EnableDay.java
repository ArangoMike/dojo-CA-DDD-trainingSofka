package co.com.sofka.model.agenda.values;

import co.com.sofka.model.generic.ValueObject;

public class EnableDay implements ValueObject<Boolean> {

    private Boolean enableDay;

    public EnableDay(Boolean enable) {
        this.enableDay = enable;
    }

    @Override
    public Boolean value() {
        return enableDay;
    }
}
