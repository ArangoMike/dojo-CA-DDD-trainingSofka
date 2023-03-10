package co.com.sofka.model.patient;


import co.com.sofka.model.generic.AggregateRoot;
import co.com.sofka.model.generic.DomainEvent;
import co.com.sofka.model.patient.entities.Appointment;
import co.com.sofka.model.patient.events.AppointmentAssociated;
import co.com.sofka.model.patient.events.EnablePatientModified;
import co.com.sofka.model.patient.events.PatientCreated;
import co.com.sofka.model.patient.events.MedicalHistoryofAppointmentAdded;
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
                   FullName fullName,TypeId typeId,Enable enable) {
        super(entityId);
        subscribe(new PatientChange(this));
    appendChange(new PatientCreated(fullName.value(),typeId.value())).apply();
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

    public void AddMedicalHistoryofAppointment(PatientId patientId, AppointmentId appointmentId, MedicalHistory medicalHistory){
        Objects.requireNonNull(patientId);
        Objects.requireNonNull(appointmentId);
        appendChange(new MedicalHistoryofAppointmentAdded(patientId.value(),appointmentId.value(),medicalHistory.value())).apply();
    }

    public void ModifyEnable(PatientId patientId,Enable enable){
        Objects.requireNonNull(patientId);
        Objects.requireNonNull(enable);
        appendChange(new EnablePatientModified(patientId.value(),enable.value()));
    }

}
