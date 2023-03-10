package co.com.sofka.mongo.PatientRepository;

import co.com.sofka.mongo.data.PatientDocument;
import co.com.sofka.usecase.patient.commands.CreatePatientCommand;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import reactor.core.publisher.Mono;

public interface MongoDBRepository extends ReactiveMongoRepository<PatientDocument, String>, ReactiveQueryByExampleExecutor<PatientDocument> {

}
