package co.com.sofka.usecase.agenda.commands;


import co.com.sofka.usecase.generic.Command;


public class DisableScheduleDayCommand extends Command {

    private String agendaId;
    private String patientId;
    private String dayName;
    private String schedule;
    private Boolean enable;

    public DisableScheduleDayCommand(){}

    public DisableScheduleDayCommand(String agendaId, String patientId, String dayName, String schedule, Boolean enable) {
        this.agendaId = agendaId;
        this.patientId = patientId;
        this.dayName = dayName;
        this.schedule = schedule;
        this.enable = enable;
    }

    public String getAgendaId() {
        return agendaId;
    }

    public void setAgendaId(String agendaId) {
        this.agendaId = agendaId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }
}
