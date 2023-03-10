package co.com.sofka.model.events.gateways;

import co.com.sofka.model.generic.DomainEvent;

public interface EventBus {
    void publish(DomainEvent event,String email);

}
