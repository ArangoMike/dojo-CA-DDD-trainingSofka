package co.com.sofka.mongo;


import co.com.sofka.model.generic.DomainEvent;
import co.com.sofka.mongo.data.StoredEvent;
import co.com.sofka.serializer.JSONMapper;
import co.com.sofka.usecase.gateways.DomainEventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.Date;

@Slf4j
@Repository
public class MongoRepositoryDomainEventAdapter implements DomainEventRepository  {

    private final ReactiveMongoTemplate template;

    private final JSONMapper eventSerializer;

    public MongoRepositoryDomainEventAdapter(ReactiveMongoTemplate template, JSONMapper eventSerializer) {
        this.template = template;
        this.eventSerializer = eventSerializer;
    }


    @Override
    public Flux<DomainEvent> findById(String aggregateId) {
        var query = new Query(Criteria.where("aggregateRootId").is(aggregateId));

        return template.find(query, StoredEvent.class)
                .sort(Comparator.comparing(event -> event.getOccurredOn()))
                .map(storeEvent -> storeEvent.deserializeEvent(eventSerializer));
    }

    @Override
    public Mono<DomainEvent> saveEvent(DomainEvent event) {
        StoredEvent eventStored = new StoredEvent();
        eventStored.setAggregateRootId(event.aggregateRootId());
        eventStored.setTypeName(event.getClass().getTypeName());
        eventStored.setOccurredOn(new Date());
        eventStored.setEventBody(StoredEvent.wrapEvent(event, eventSerializer));
        return template.save(eventStored)
                .map(storeEvent -> storeEvent.deserializeEvent(eventSerializer));
    }

/*
    @Override
    public Mono<DomainEvent> updateEnablePatientEvent(DomainEvent event) {
        var newevent = eventSerializer.writeToJson(event);
        Update update = new Update().set("eventBody.enable", newevent.);

        return mongoTemplate.updateFirst(
                Query.query(Criteria.where("id").is(event.getId())),
                update,
                StoredEvent.class
        ).flatMap(result -> {
            if (result.getModifiedCount() == 1) {
                // Se actualizó correctamente, devolver el evento actualizado
                return Mono.just(event);
            } else {
                // No se actualizó ningún evento, devolver un error
                return Mono.error(new RuntimeException("No se pudo actualizar el evento"));
            }
        });
    }

 */



}
