package co.com.sofka.api;

import co.com.sofka.model.generic.DomainEvent;
import co.com.sofka.usecase.patient.*;
import co.com.sofka.usecase.patient.commands.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class PatientRestController {
    @Bean
    public RouterFunction<ServerResponse> createPatient(CreatePatientUseCase useCase) {

        return route(
                POST("/create/patient").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(useCase
                                        .apply(request.bodyToMono(CreatePatientCommand.class)),
                                DomainEvent.class))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> associateAppointment(AssociateAppointmentUseCase useCase) {

        return route(
                POST("/add/appointment").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(useCase
                                        .apply(request.bodyToMono(AssociateAppointmentCommand.class)),
                                DomainEvent.class))
        );
    }


    @Bean
    public RouterFunction<ServerResponse> validateAppointment(AssociateAppointmentUseCase useCase) {

        return route(
                POST("/validate/appointment/{agendaid}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(useCase
                                .validationsAppointment(request.bodyToMono(AssociateAppointmentCommand.class),request.pathVariable("agendaid")),
                                Boolean.class))
        );

    }


    @Bean
    public RouterFunction<ServerResponse> updateEnablePatient(ModifyEnablePatientUseCase useCase){

        return route(
                POST("/update/enable/patient").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(useCase
                                        .apply(request.bodyToMono(ModifyEnablePatientCommand.class)),
                                DomainEvent.class))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> modifyEmailPatient(ModifyEmailPatientUseCase useCase){

        return route(
                POST("/modify/email/patient").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(useCase
                                        .apply(request.bodyToMono(ModifyEmailPatientCommand.class)),
                                DomainEvent.class))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> assignMedicalCheckupAppointment(AssignMedicalCheckupAppointmentUseCase useCase){

        return route(
                POST("/assign/medicalcheckup/patient").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(useCase
                                        .apply(request.bodyToMono(AssignMedicalCheckupAppointmentCommand.class)),
                                DomainEvent.class))
        );
    }

}
