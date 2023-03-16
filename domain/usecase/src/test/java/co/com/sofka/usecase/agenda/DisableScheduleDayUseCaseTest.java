package co.com.sofka.usecase.agenda;

import co.com.sofka.model.agenda.events.AgendaCreated;
import co.com.sofka.model.agenda.events.DayAssociated;
import co.com.sofka.model.agenda.events.DayScheduleDisabled;
import co.com.sofka.model.agenda.values.Schedule;
import co.com.sofka.model.generic.DomainEvent;
import co.com.sofka.usecase.agenda.commands.DisableScheduleDayCommand;
import co.com.sofka.usecase.gateways.AgendaRepository;
import co.com.sofka.usecase.gateways.DomainEventRepository;
import co.com.sofka.usecase.patient.ModifyEnablePatientUseCase;
import co.com.sofka.usecase.patient.commands.ModifyEnablePatientCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class DisableScheduleDayUseCaseTest {
    @Mock
    private DomainEventRepository repository;
    @Mock
    private AgendaRepository agendaRepository;
    private DisableScheduleDayUseCase useCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = new DisableScheduleDayUseCase(repository,agendaRepository);
    }

    @Test
    void apply() {

        String agendaId= "agendaId";
        String patientId = "patientId";
        String dayName = "MONDAY";
        String schedule = "09:00";
        Boolean enable = false;

        DisableScheduleDayCommand command = new DisableScheduleDayCommand(agendaId,patientId,dayName,schedule, enable);

        DayScheduleDisabled eventRes = new DayScheduleDisabled(agendaId,patientId,dayName,schedule,enable);

        Mockito.when(repository.findById(any(String.class))).thenReturn(events());
        Mockito.when(agendaRepository.disableScheduleDay(any(DisableScheduleDayCommand.class))).thenReturn(Mono.empty());
        Mockito.when(repository.saveEvent(any(DayScheduleDisabled.class))).thenReturn(Mono.just(eventRes));

        //Act
        Flux<DomainEvent> result = useCase.apply(Mono.just(command));

        StepVerifier.create(result)
                //Assert
                .expectNextMatches(event1 -> event1 instanceof DayScheduleDisabled &&
                        ((DayScheduleDisabled) event1).getDayName().equals(command.getDayName()) &&
                        ((DayScheduleDisabled) event1).getAgendaId().equals(command.getAgendaId()) &&
                        ((DayScheduleDisabled) event1).getSchedule().equals(command.getSchedule())
                )
                .expectComplete()
                .verify();

    }
    private Flux<DomainEvent> events() {
        List schedules = new ArrayList<>();
        Schedule schedule1 = new Schedule("08:00",true);
        Schedule schedule2 = new Schedule("09:00",true);
        schedules.add(schedule1);
        schedules.add(schedule2);

        return Flux.just(new AgendaCreated(
                "2023/03/06 08:00","2023/03/10 16:00"),
                new DayAssociated("dayId","MONDAY",schedules));
    }
}