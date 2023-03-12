package co.com.sofka.model.agenda.values;

import co.com.sofka.model.generic.ValueObject;

public class Schedule implements ValueObject<Schedule.Props> {

    private String schedule;

    private Boolean enable;

    public Schedule(){}



    public Schedule(String schedule,Boolean enable){
        this.schedule = schedule;
        this.enable = enable;
    }

    @Override
    public Schedule.Props value() {

        return new Schedule.Props(){
         @Override
         public String schedule(){
             return schedule;
         }

         @Override
            public Boolean enable(){
             return enable;
         }
        };

    }

    public interface Props{
        String schedule();
        Boolean enable();
    }
}
