package co.com.sofka.model.agenda.events;

import co.com.sofka.model.agenda.values.Schedule;
import co.com.sofka.model.generic.DomainEvent;

import java.util.List;

public class DayAssociated extends DomainEvent {

    private String dayId;
    private String dayName;
    private String enableDay;
    private List<?> schedules;

    public DayAssociated(){super("ramirez.fernandez.dayassocated");}
    public DayAssociated(String dayId,String dayName, List<?> schedules) {
        super("ramirez.fernandez.dayassocated");
        this.dayId = dayId;
        this.enableDay = "true";
        this.dayName = dayName;
        this.schedules = schedules;
    }

    public String getEnableDay() {
        return enableDay;
    }

    public String getDayId() {
        return dayId;
    }

    public String getDayName() {
        return dayName;
    }

    public List<?> getSchedules() {
        return schedules;
    }
}
