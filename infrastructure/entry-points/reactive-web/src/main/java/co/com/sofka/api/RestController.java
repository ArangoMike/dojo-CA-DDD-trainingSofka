package co.com.sofka.api;

import co.com.sofka.model.generic.DomainEvent;
import co.com.sofka.usecase.patient.AssociateAppointmentUseCase;
import co.com.sofka.usecase.patient.CreatePatientUseCase;
import co.com.sofka.usecase.patient.commands.AssociateAppointmentCommand;
import co.com.sofka.usecase.patient.commands.CreatePatientCommand;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RestController {
    @Bean
    public RouterFunction<ServerResponse> createPatient(CreatePatientUseCase useCase){

        return route(
                POST("/create/patient").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(useCase
                                .apply(request.bodyToMono(CreatePatientCommand.class)),
                                DomainEvent.class))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> associateAppointment(AssociateAppointmentUseCase useCase){

        return route(
                POST("/add/appointment").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(useCase
                                .apply(request.bodyToMono(AssociateAppointmentCommand.class)),
                                DomainEvent.class))
        );
    }

}
