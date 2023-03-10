package co.com.sofka.mongo.PatientRepository;

import co.com.sofka.mongo.data.PatientDocument;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;


public interface MongoDBRepository extends ReactiveMongoRepository<PatientDocument, String>, ReactiveQueryByExampleExecutor<PatientDocument> {

}
