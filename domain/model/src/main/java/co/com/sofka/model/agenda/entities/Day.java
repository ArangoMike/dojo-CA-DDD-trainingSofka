package co.com.sofka.model.agenda.entities;

import co.com.sofka.model.agenda.values.DayId;
import co.com.sofka.model.agenda.values.DayName;
import co.com.sofka.model.agenda.values.EnableDay;
import co.com.sofka.model.agenda.values.Schedule;
import co.com.sofka.model.generic.Entity;

import java.util.List;

public class Day extends Entity<DayId> {

    private DayName dayName;
    private EnableDay enable;
    private List<Schedule> schedules;

    public Day(DayId entityId, DayName dayName, EnableDay enable, List<Schedule> schedules) {
        super(entityId);
        this.dayName = dayName;
        this.enable = enable;
        this.schedules = schedules;
    }

    public DayName DayName() {
        return dayName;
    }

    public EnableDay Enable() {
        return enable;
    }

    public List<Schedule> Schedules() {
        return schedules;
    }
}
