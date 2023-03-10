package co.com.sofka.model.agenda.values;

import co.com.sofka.model.generic.ValueObject;

public class InitialDate implements ValueObject<String> {

    private String initialDate;


    public InitialDate(String initialDate) {
        this.initialDate = initialDate;

    }

    @Override
    public String value() {
        return initialDate;
    }
}
