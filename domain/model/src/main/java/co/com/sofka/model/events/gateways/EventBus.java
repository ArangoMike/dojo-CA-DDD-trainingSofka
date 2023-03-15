package co.com.sofka.model.events.gateways;

import co.com.sofka.model.generic.DomainEvent;
import org.reactivestreams.Publisher;

public interface EventBus {
    Publisher<?> publish(DomainEvent event, String email);

}
