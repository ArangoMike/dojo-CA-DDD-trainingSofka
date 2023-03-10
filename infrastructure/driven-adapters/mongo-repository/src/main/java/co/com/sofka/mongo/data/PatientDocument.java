package co.com.sofka.mongo.data;




import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.List;


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
    protected String email;

    protected List<Object> appointments;



}
