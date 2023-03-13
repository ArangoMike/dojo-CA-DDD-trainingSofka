package co.com.sofka.events;


import co.com.sofka.events.data.Notification;
import co.com.sofka.model.agenda.events.AgendaDayScheduleAssigned;
import co.com.sofka.serializer.JSONMapper;
import co.com.sofka.serializer.JSONMapperImpl;
import co.com.sofka.usecase.agenda.AssociatePatientEventUseCase;
import co.com.sofka.usecase.patient.AssociateAppointmentEventUseCase;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

@Component
public class RabbitMqEventHandler {

    public static final String EVENTS_QUEUE2 = "events.queue.2";

    private final Logger logger = Logger.getLogger("RabbitMqEventHandler");
    private final JSONMapper mapper = new JSONMapperImpl();

    private final AssociateAppointmentEventUseCase useCase1;

    private final AssociatePatientEventUseCase useCase2;

    public RabbitMqEventHandler(AssociateAppointmentEventUseCase useCase1,AssociatePatientEventUseCase useCase2) {
        this.useCase1 = useCase1;
        this.useCase2 = useCase2;
    }

    @RabbitListener(queues = EVENTS_QUEUE2)
    public void listener2(String message) throws ClassNotFoundException {
        Notification notification = Notification.from(message);
        if(notification.getType()
                .equals("co.com.sofka.model.agenda.events.AgendaDayScheduleAssigned")){
            logger.info("1: " + notification.toString());
            this.useCase1.apply(Mono
                    .just((AgendaDayScheduleAssigned) mapper.readFromJson(notification.getBody(),
                            AgendaDayScheduleAssigned.class)))
                    .subscribe();

            this.useCase2.apply(Mono
                            .just((AgendaDayScheduleAssigned) mapper.readFromJson(notification.getBody(),
                                    AgendaDayScheduleAssigned.class)))
                    .subscribe();

        }else{
            logger.info("1: " + "we currently don't have a listener for that event " +notification.toString());
        }
    }



}
