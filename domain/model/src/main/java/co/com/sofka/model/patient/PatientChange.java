package co.com.sofka.model.patient;

import co.com.sofka.model.generic.EventChange;
import co.com.sofka.model.patient.entities.Appointment;
import co.com.sofka.model.patient.events.AppointmentAssociated;
import co.com.sofka.model.patient.events.EnablePatientModified;
import co.com.sofka.model.patient.events.PatientCreated;
import co.com.sofka.model.patient.events.PatientUpdated;
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

        apply((PatientUpdated event)-> {
            patient.fullName = new FullName(event.getFullName());
            patient.typeId = new TypeId(event.getTypeId());
            patient.enable = new Enable("true");
            patient.email = new Email(event.getEmail());
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
