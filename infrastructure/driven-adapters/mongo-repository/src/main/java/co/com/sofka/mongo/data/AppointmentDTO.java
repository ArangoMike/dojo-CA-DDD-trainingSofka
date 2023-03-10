package co.com.sofka.mongo.data;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AppointmentDTO {

    protected String appointmentId;
    protected  String appointmentDate;


}
