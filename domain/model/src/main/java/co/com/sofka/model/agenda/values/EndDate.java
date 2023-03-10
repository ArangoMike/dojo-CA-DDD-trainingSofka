package co.com.sofka.model.agenda.values;

import co.com.sofka.model.generic.ValueObject;

public class EndDate implements ValueObject<String> {
    private String endDate;

    public EndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String value() {
        return endDate;
    }
}
