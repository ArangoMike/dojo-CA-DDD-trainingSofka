package co.com.sofka.mongo.AgendaRepository;


import co.com.sofka.mongo.data.AgendaDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;


public interface MongoDBAgendaRepository extends ReactiveMongoRepository<AgendaDocument, String>, ReactiveQueryByExampleExecutor<AgendaDocument> {


}
