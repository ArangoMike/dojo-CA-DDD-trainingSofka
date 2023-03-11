package co.com.sofka.model.patient;


import co.com.sofka.model.generic.AggregateRoot;
import co.com.sofka.model.generic.DomainEvent;
import co.com.sofka.model.patient.entities.Appointment;
import co.com.sofka.model.patient.events.*;
import co.com.sofka.model.patient.values.*;

import java.util.List;
import java.util.Objects;

public class Patient extends AggregateRoot<PatientId> {

    protected FullName fullName;
    protected Email email;
    protected TypeId typeId;
    protected Enable enable;

    protected List<Appointment> appointments;


    public Patient(PatientId entityId,
                   FullName fullName,TypeId typeId,Enable enable,Email email) {
        super(entityId);
        subscribe(new PatientChange(this));
    appendChange(new PatientCreated(fullName.value(),typeId.value(),email.value())).apply();
    }

    private Patient(PatientId id){
        super(id);
        subscribe(new PatientChange(this));
    }

    public static Patient from(PatientId id, List<DomainEvent> events){
        Patient patient = new Patient(id);
        events.forEach(event -> patient.applyEvent(event));
        return patient;
    }

    public void AssociateAppointment(AppointmentDate appointmentDate,AppointmentId appointmentId){
        Objects.requireNonNull(appointmentDate);
        appendChange(new AppointmentAssociated(appointmentDate.value(),appointmentId.value())).apply();
    }


    public void ModifyEnable(PatientId patientId,Enable enable){
        Objects.requireNonNull(patientId);
        Objects.requireNonNull(enable);
        appendChange(new EnablePatientModified(patientId.value(),enable.value()));
    }

    public void ModifyEmailPatient(PatientId patientId,Email email){
        Objects.requireNonNull(patientId);
        Objects.requireNonNull(email);
        appendChange(new EmailPatientModified(patientId.value(),email.value()));
    }

    public void AssingMedicalCheckupAppointment(PatientId patientId, AppointmentId appointmentId, MedicalCheckup medicalCheckup){
        Objects.requireNonNull(patientId);
        Objects.requireNonNull(appointmentId);
        appendChange(new MedicalCheckupAppointmentAssigned(patientId.value(),appointmentId.value(),medicalCheckup.value())).apply();
    }

}
