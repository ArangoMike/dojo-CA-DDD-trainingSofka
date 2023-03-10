package co.com.sofka.mongo.data;



import co.com.sofka.model.patient.events.AppointmentAssociated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PatientDocument {


    @Id
    protected String patientId;
    protected String fullName;
    protected String typeId;
    protected String enable;

    protected List<Object> appointments;


    public interface Appointment {
       public String appointmentId = "";
       public String appointmentDate ="";
    }
}
