package co.com.sofka.model.patient;

import co.com.sofka.model.generic.EventChange;
import co.com.sofka.model.patient.entities.Appointment;
import co.com.sofka.model.patient.events.AppointmentAssociated;
import co.com.sofka.model.patient.events.EnableModified;
import co.com.sofka.model.patient.events.PatientCreated;
import co.com.sofka.model.patient.values.*;

import java.util.ArrayList;


public class PatientChange extends EventChange {


    public PatientChange(Patient patient){

        apply((PatientCreated event)-> {
            patient.fullName = new FullName(event.getFullName());
            patient.enable = new Enable(true);
            patient.appointments = new ArrayList<>();
                    });

        apply((AppointmentAssociated event) -> {
            var appointmentId = event.getAppointmentId();
            Appointment appointment = new Appointment(AppointmentId.of(event.getAppointmentId()),
                    new AppointmentDate(event.getAppointmentDate()));
            patient.appointments.add(appointment);
        });

        apply((EnableModified event) -> {
            patient.enable = new Enable(event.getEnable());
        });


/*  COMO AGREGAR HISTORIAL MEDICO A ENTITY APPOINTMENT

        apply((MedicalHistoryofAppointmentAdded event) -> {
            MedicalHistory medicalHistory = new MedicalHistory(event.getMedicalHistory());

            patient.appointmentId = event.getAppointmentId();

        });


        apply((CommentAdded event)-> {
            Comment comment = new Comment(CommentId.of(event.getId()),
                    new Author(event.getAuthor()),
                    new Content(event.getContent()));
            post.comments.add(comment);
        });
        */

    }
}
