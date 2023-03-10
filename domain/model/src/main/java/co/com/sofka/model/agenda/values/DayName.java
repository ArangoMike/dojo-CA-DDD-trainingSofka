package co.com.sofka.model.agenda.values;

import co.com.sofka.model.generic.ValueObject;

public class DayName implements ValueObject<String> {

    private String dayName;

    public DayName(String dayName){
        this.dayName = dayName;
    }

    @Override
    public String value() {
        return dayName;
    }
}
