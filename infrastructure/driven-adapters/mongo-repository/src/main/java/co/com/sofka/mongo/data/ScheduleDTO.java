package co.com.sofka.mongo.data;

public class ScheduleDTO {

    private String schedule;

    private Boolean enable;


    public ScheduleDTO(String schedule, Boolean enable) {
        this.schedule = schedule;
        this.enable = enable;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}
