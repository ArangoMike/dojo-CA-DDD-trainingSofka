package co.com.sofka.usecase.agenda;



import co.com.sofka.usecase.agenda.commands.CreateAgendaCommand;
import co.com.sofka.usecase.gateways.AgendaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class GetAgendaUseCaseTest {

    @Mock
    private AgendaRepository agendaRepository;
    private GetAgendaUseCase useCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = new GetAgendaUseCase(agendaRepository);
    }

    @Test
    void apply() {

        String agendaId = "agendaid";
        String initialDate = "2023/03/06 08:00";
        String endDate = "2023/03/10 16:00";
        CreateAgendaCommand command = new CreateAgendaCommand(agendaId, initialDate, endDate);

        Mockito.when(agendaRepository.getAgendaByid(any(String.class))).thenReturn(Mono.just(command));


        //Act
        Mono<CreateAgendaCommand> result = useCase.apply(agendaId);

        StepVerifier.create(result)
                //Assert
                .expectNextMatches(event1 -> event1 instanceof CreateAgendaCommand &&
                        event1.getInitialDate().equals(command.getInitialDate()) &&
                        event1.getAgendaId().equals(command.getAgendaId()) &&
                        event1.getEndDate().equals(command.getEndDate())
                )
                .expectComplete()
                .verify();

    }
}