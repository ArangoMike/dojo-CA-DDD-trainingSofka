package co.com.sofka.model.agenda.entities;

import co.com.sofka.model.agenda.values.DayId;
import co.com.sofka.model.agenda.values.DayName;
import co.com.sofka.model.agenda.values.Schedule;
import co.com.sofka.model.generic.Entity;

import java.util.List;

public class Day extends Entity<DayId> {

    private DayName dayName;
    private List<Schedule> schedules;

    public Day(DayId entityId, DayName dayName, List<Schedule> schedules) {
        super(entityId);
        this.dayName = dayName;
        this.schedules = schedules;
    }

    public DayName DayName() {
        return dayName;
    }

    public List<Schedule> Schedules() {
        return schedules;
    }
}
