package co.com.sofka.mongo.data;

import java.util.List;

public class DayDTO {

    private String dayId;
    private String dayName;
    private List<ScheduleDTO> schedules;

    public DayDTO(String dayId, String dayName, List<ScheduleDTO> schedules) {
        this.dayId = dayId;
        this.dayName = dayName;
        this.schedules = schedules;
    }

    public String getDayId() {
        return dayId;
    }

    public void setDayId(String dayId) {
        this.dayId = dayId;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public List<ScheduleDTO> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<ScheduleDTO> schedules) {
        this.schedules = schedules;
    }
}
