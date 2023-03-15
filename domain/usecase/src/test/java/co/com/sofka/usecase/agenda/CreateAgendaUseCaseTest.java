package co.com.sofka.usecase.agenda;


import co.com.sofka.model.agenda.events.AgendaCreated;
import co.com.sofka.model.generic.DomainEvent;
import co.com.sofka.usecase.agenda.commands.CreateAgendaCommand;
import co.com.sofka.usecase.gateways.AgendaRepository;
import co.com.sofka.usecase.gateways.DomainEventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CreateAgendaUseCaseTest {

    private CreateAgendaUseCase createAgendaUseCase;
    private DomainEventRepository eventRepository;
    private AgendaRepository agendaRepository;

    @BeforeEach
    void setUp() {
        eventRepository = mock(DomainEventRepository.class);
        agendaRepository = mock(AgendaRepository.class);
        createAgendaUseCase = new CreateAgendaUseCase(eventRepository, agendaRepository);
    }

    @Test
    void apply() {
        // Arrange
        String agendaId = "agenda1";
        String initialDate = "2023/03/06 08:00";
        String endDate = "2023/03/10 16:00";
        CreateAgendaCommand command = new CreateAgendaCommand(agendaId, initialDate, endDate);
        AgendaCreated agendaCreated = new AgendaCreated(initialDate, endDate);

        when(agendaRepository.createAgenda(any(CreateAgendaCommand.class))).thenReturn(Mono.empty());
        when(eventRepository.saveEvent(any(DomainEvent.class))).thenReturn(Mono.just(agendaCreated));

        // Act
        Flux<DomainEvent> result = createAgendaUseCase.apply(Mono.just(command));

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(event -> event instanceof AgendaCreated &&
                        ((AgendaCreated) event).getEndDate().equals(endDate) &&
                        ((AgendaCreated) event).getInitialDate().equals(initialDate))
                .expectComplete()
                .verify();

    }


}