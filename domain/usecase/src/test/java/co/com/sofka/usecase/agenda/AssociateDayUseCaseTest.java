package co.com.sofka.usecase.agenda;

import co.com.sofka.model.agenda.events.AgendaCreated;
import co.com.sofka.model.agenda.events.DayAssociated;
import co.com.sofka.model.agenda.values.Schedule;
import co.com.sofka.model.events.gateways.EventBus;
import co.com.sofka.model.generic.DomainEvent;
import co.com.sofka.model.patient.events.AppointmentAssociated;
import co.com.sofka.model.patient.events.PatientCreated;
import co.com.sofka.model.patient.values.Email;
import co.com.sofka.model.patient.values.FullName;
import co.com.sofka.model.patient.values.TypeId;
import co.com.sofka.usecase.agenda.commands.AssociateDayCommand;
import co.com.sofka.usecase.gateways.AgendaRepository;
import co.com.sofka.usecase.gateways.DomainEventRepository;
import co.com.sofka.usecase.gateways.PatientRepository;
import co.com.sofka.usecase.patient.AssociateAppointmentEventUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;


class AssociateDayUseCaseTest {

    @Mock
    private DomainEventRepository repository;

    @Mock
    private AgendaRepository agendaRepository;

    private AssociateDayUseCase useCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = new AssociateDayUseCase(repository, agendaRepository);
    }


    @Test
    void apply() {

        List schedules = new ArrayList<>();
        Schedule schedule1 = new Schedule("08:00",true);
        Schedule schedule2 = new Schedule("09:00",true);
        schedules.add(schedule1);
        schedules.add(schedule2);

        AssociateDayCommand event = new AssociateDayCommand("dayId","agenda1","MONDAY",schedules);

        DayAssociated dayAssociated = new DayAssociated("dayId","MONDAY",schedules);

        Mockito.when(repository.findById(any(String.class))).thenReturn(events());
        Mockito.when(agendaRepository.addDayAgenda(any(AssociateDayCommand.class))).thenReturn(Mono.empty());
        Mockito.when(repository.saveEvent(any(DayAssociated.class))).thenReturn(Mono.just(dayAssociated));

        //Act
        Flux<DomainEvent> result = useCase.apply(Mono.just(event));


        StepVerifier.create(result)
                //Assert
                .expectNextMatches(event1 -> event1 instanceof DayAssociated &&
                        ((DayAssociated) event1).getDayName().equals("MONDAY") &&
                        ((DayAssociated) event1).getSchedules().equals(event.getSchedules())
                )
                .expectComplete()
                .verify();

    }

    private Flux<DomainEvent> events() {
        return Flux.just(new AgendaCreated(
                "2023/03/06 08:00","2023/03/10 16:00"));
    }

}