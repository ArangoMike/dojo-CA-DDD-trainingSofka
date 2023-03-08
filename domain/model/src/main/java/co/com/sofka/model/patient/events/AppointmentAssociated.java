package co.com.sofka.model.patient.events;

import co.com.sofka.model.generic.DomainEvent;
import co.com.sofka.model.patient.values.AppointmentId;
import co.com.sofka.model.patient.values.PatientId;

import java.util.Date;

public class AppointmentAssociated extends DomainEvent {

    private  String appointmentId;
    private  String appointmentDate;

    public AppointmentAssociated(){
        super("ramirez.fernandez.AppointmentAssociated");
    }


    public AppointmentAssociated(String appointmentDate, String appointmentId) {
        super("ramirez.fernandez.AppointmentAssociated");
        this.appointmentId = appointmentId;
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

}
