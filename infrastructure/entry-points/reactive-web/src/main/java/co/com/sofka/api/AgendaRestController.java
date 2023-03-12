package co.com.sofka.api;

import co.com.sofka.model.generic.DomainEvent;
import co.com.sofka.usecase.agenda.AssociateDayUseCase;
import co.com.sofka.usecase.agenda.CreateAgendaUseCase;
import co.com.sofka.usecase.agenda.DisableScheduleDayUseCase;
import co.com.sofka.usecase.agenda.GetAgendaUseCase;
import co.com.sofka.usecase.agenda.commands.AssociateDayCommand;
import co.com.sofka.usecase.agenda.commands.CreateAgendaCommand;
import co.com.sofka.usecase.agenda.commands.DisableScheduleDayCommand;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class AgendaRestController {

    @Bean
    public RouterFunction<ServerResponse> createAgenda(CreateAgendaUseCase useCase){

        return route(
                POST("/create/agenda").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(useCase
                                        .apply(request.bodyToMono(CreateAgendaCommand.class)),
                                DomainEvent.class))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> associateDay(AssociateDayUseCase useCase){

        return route(
                POST("/add/day").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(useCase
                                        .apply(request.bodyToMono(AssociateDayCommand.class)),
                                DomainEvent.class))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> disableScheduleDay(DisableScheduleDayUseCase useCase){

        return route(
                POST("/disable/schedule/day").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(useCase
                                        .apply(request.bodyToMono(DisableScheduleDayCommand.class)),
                                DomainEvent.class))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> getAgendaById(GetAgendaUseCase useCase){

        return route(
                GET("/get/Agenda/{id}"),
                request -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(useCase
                                        .apply((request.pathVariable("id"))),
                                CreateAgendaCommand.class))
        );
    }

}
