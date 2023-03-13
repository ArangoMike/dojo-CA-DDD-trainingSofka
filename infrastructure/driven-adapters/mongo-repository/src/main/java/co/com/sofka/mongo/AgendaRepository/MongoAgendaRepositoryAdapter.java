package co.com.sofka.mongo.AgendaRepository;


import co.com.sofka.mongo.data.AgendaDocument;

import co.com.sofka.mongo.helper.AdapterOperations;
import co.com.sofka.usecase.agenda.commands.CreateAgendaCommand;

import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;


@Repository
public class MongoAgendaRepositoryAdapter extends AdapterOperations<CreateAgendaCommand, AgendaDocument, String, MongoDBAgendaRepository>
{

    public MongoAgendaRepositoryAdapter(MongoDBAgendaRepository repository, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, d -> mapper.map(d, CreateAgendaCommand.class));
    }

}
