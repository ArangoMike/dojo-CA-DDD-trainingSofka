package co.com.sofka.model.agenda.events;


import co.com.sofka.model.generic.DomainEvent;

import java.util.List;

public class DayAssociated extends DomainEvent {

    private String dayId;
    private String dayName;
    private List<?> schedules;

    public DayAssociated(){super("ramirez.fernandez.dayassociated");}
    public DayAssociated(String dayId,String dayName, List<?> schedules) {
        super("ramirez.fernandez.dayassociated");
        this.dayId = dayId;
        this.dayName = dayName;
        this.schedules = schedules;
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
