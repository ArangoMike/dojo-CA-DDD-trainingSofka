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
public class AgendaDocument {

    @Id
    protected String agendaId;
    protected String initialDate;
    protected String endDate;
    protected List<String> patients;
    protected List<DayDTO> days;

}
