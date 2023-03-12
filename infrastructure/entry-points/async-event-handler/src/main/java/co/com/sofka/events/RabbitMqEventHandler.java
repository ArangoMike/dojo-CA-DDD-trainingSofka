package co.com.sofka.events;


import co.com.sofka.events.data.Notification;
import co.com.sofka.model.agenda.events.AgendaDayScheduleAssigned;
import co.com.sofka.model.patient.events.AppointmentAssociated;
import co.com.sofka.serializer.JSONMapper;
import co.com.sofka.serializer.JSONMapperImpl;
import co.com.sofka.usecase.agenda.commands.AssignScheduleDayAgendaCommand;
import co.com.sofka.usecase.patient.AssociateAppointmentEventUseCase;
import co.com.sofka.usecase.patient.AssociateAppointmentUseCase;
import co.com.sofka.usecase.patient.commands.AssociateAppointmentCommand;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

@Component
public class RabbitMqEventHandler {

    public static final String EVENTS_QUEUE2 = "events.queue.2";

    private final Logger logger = Logger.getLogger("RabbitMqEventHandler");
    private final JSONMapper mapper = new JSONMapperImpl();

    private final AssociateAppointmentEventUseCase useCase;

    public RabbitMqEventHandler(AssociateAppointmentEventUseCase useCase) {
        this.useCase = useCase;
    }

    @RabbitListener(queues = EVENTS_QUEUE2)
    public void listener(String message) throws ClassNotFoundException {
        Notification notification = Notification.from(message);
        if(notification.getType()
                .equals("co.com.sofka.model.agenda.events.AgendaDayScheduleAssigned")){
            logger.info("1: " + notification.toString());
            this.useCase.apply(Mono
                    .just((AgendaDayScheduleAssigned) mapper.readFromJson(notification.getBody(),
                            AgendaDayScheduleAssigned.class)))
                    .subscribe();

        }else{
            logger.info("1: " + "we currently don't have a listener for that event " +notification.toString());
        }
    }


}
