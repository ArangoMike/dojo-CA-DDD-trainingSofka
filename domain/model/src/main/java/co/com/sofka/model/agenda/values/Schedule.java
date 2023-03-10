package co.com.sofka.model.agenda.values;

import co.com.sofka.model.generic.ValueObject;

public class Schedule implements ValueObject<String> {

    private String schedule;

    private Boolean enable;

    public Schedule(String schedule,Boolean enable){
        this.schedule = schedule;
        this.enable = enable;
    }

    @Override
    public String value() {
        return schedule;
    }
}
