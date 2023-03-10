package co.com.sofka.model.agenda.values;

import co.com.sofka.model.generic.Identity;
import co.com.sofka.model.patient.values.PatientId;

public class DayId extends Identity {

    private DayId(String uuid) {
        super(uuid);
    }

    public DayId() {
    }

    public static DayId of(String uuid){
        return new DayId(uuid);
    }
}
