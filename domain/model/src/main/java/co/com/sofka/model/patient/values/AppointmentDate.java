package co.com.sofka.model.patient.values;

import co.com.sofka.model.generic.ValueObject;

import java.util.Date;

public class AppointmentDate implements ValueObject<String> {

    private String appointmentDate;

    public AppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    @Override
    public String value() {
        return appointmentDate;
    }

}
