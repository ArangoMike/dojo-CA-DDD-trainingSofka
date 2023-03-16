package co.com.sofka.model.patient;

import co.com.sofka.model.generic.EventChange;
import co.com.sofka.model.patient.entities.Appointment;
import co.com.sofka.model.patient.events.*;
import co.com.sofka.model.patient.values.*;

import java.util.ArrayList;


public class PatientChange extends EventChange {

    public PatientChange(Patient patient){

        apply((PatientCreated event)-> {
            patient.fullName = new FullName(event.getFullName());
            patient.typeId = new TypeId(event.getTypeId());
            patient.enable = new Enable("true");
            patient.email = new Email(event.getEmail());
            patient.appointments = new ArrayList<>();
                    });

        apply((AppointmentAssociated event) -> {
            Appointment appointment = new Appointment(AppointmentId.of(event.getAppointmentId()),
                    new AppointmentDate(event.getAppointmentDate()));
            patient.appointments.add(appointment);
        });

        apply((EnablePatientModified event) -> {
            patient.enable = new Enable(event.getEnable());
        });

        apply((EmailPatientModified event)-> {
            patient.email = new Email(event.getEmail());
        });

        apply((MedicalCheckupAppointmentAssigned event) -> {

            patient.appointments.forEach(appointment -> {
                if (appointment.identity().value() == event.getAppointmentId()) {
                    appointment.assignMedicalCheckup(event.getMedicalCheckup());
                }});
        });


    }
}
