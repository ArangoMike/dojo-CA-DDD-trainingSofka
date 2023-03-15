package co.com.sofka.mongo.PatientRepository;



import co.com.sofka.mongo.data.PatientDocument;
import co.com.sofka.mongo.helper.AdapterOperations;
import co.com.sofka.usecase.patient.commands.CreatePatientCommand;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;


@Repository
public class MongoRepositoryAdapter extends AdapterOperations<CreatePatientCommand, PatientDocument, String, MongoDBRepository>
{
    public MongoRepositoryAdapter(MongoDBRepository repository, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, d -> mapper.map(d, CreatePatientCommand.class));
    }

}
