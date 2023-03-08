package co.com.sofka.model.patient.values;

import co.com.sofka.model.generic.Identity;

public class AppointmentId extends Identity {

    private AppointmentId(String uuid) {
        super(uuid);
    }

    public AppointmentId() {
    }

    public static AppointmentId of(String uuid){
        return new AppointmentId(uuid);
    }
}
