package co.com.sofka.usecase.agenda.commands;

import co.com.sofka.usecase.generic.Command;

import java.util.List;

public class AssociateDayCommand extends Command {

    private String dayId;
    private String agendaId;
    private String dayName;
    private List<?> schedules;

    public AssociateDayCommand(){}

    public AssociateDayCommand(String dayId,String agendaId, String dayName, List<?> schedules) {
        this.dayId = dayId;
        this.agendaId = agendaId;
        this.dayName = dayName;
        this.schedules = schedules;
    }

    public String getDayId() {
        return dayId;
    }

    public void setDayId(String dayId) {
        this.dayId = dayId;
    }

    public String getAgendaId() {
        return agendaId;
    }

    public void setAgendaId(String agendaId) {
        this.agendaId = agendaId;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public List<?> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<?> schedules) {
        this.schedules = schedules;
    }
}
