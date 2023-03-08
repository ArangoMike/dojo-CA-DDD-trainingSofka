package co.com.sofka.usecase.gateways;


import co.com.sofka.model.generic.DomainEvent;
import co.com.sofka.model.patient.Patient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DomainEventRepository {

    Flux<DomainEvent> findById(String aggregateId);
    Mono<DomainEvent> saveEvent(DomainEvent event);



}
