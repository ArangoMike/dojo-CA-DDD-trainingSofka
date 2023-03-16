package co.com.sofka.usecase.agenda;

import co.com.sofka.model.agenda.events.AgendaCreated;
import co.com.sofka.model.agenda.events.AgendaDayScheduleAssigned;
import co.com.sofka.model.agenda.events.DayAssociated;
import co.com.sofka.model.agenda.events.DayScheduleDisabled;
import co.com.sofka.model.agenda.values.Schedule;
import co.com.sofka.model.events.gateways.EventBus;
import co.com.sofka.model.generic.DomainEvent;
import co.com.sofka.usecase.agenda.commands.AssignScheduleDayAgendaCommand;
import co.com.sofka.usecase.gateways.AgendaRepository;
import co.com.sofka.usecase.gateways.DomainEventRepository;
import co.com.sofka.usecase.gateways.PatientRepository;
import co.com.sofka.usecase.patient.commands.CreatePatientCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
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
import static org.mockito.ArgumentMatchers.eq;

class AssignScheduleDayAgendaUseCaseTest {
    @Mock
    private DomainEventRepository repository;
    @Mock
    private AgendaRepository agendaRepository;
    @Mock
    private PatientRepository patientRepository;
    @Mock
    private EventBus bus;

    private AssignScheduleDayAgendaUseCase useCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = new AssignScheduleDayAgendaUseCase(repository,agendaRepository,patientRepository,bus);
    }

    @Test
    void apply() {
        String agendaId = "agendaId";
        String patientId = "patientId";
        String appointmentDate = "2023/03/13 09:00";
        String fullName = "nombre usuario";
        String typeId = "CC";
        String email = "kjkj.test@sofka.com.co";
        CreatePatientCommand patient = new CreatePatientCommand(patientId, fullName, typeId, email);

        AssignScheduleDayAgendaCommand command = new AssignScheduleDayAgendaCommand(agendaId, patientId,appointmentDate);

        AgendaDayScheduleAssigned eventRes = new AgendaDayScheduleAssigned(agendaId,patientId,appointmentDate);


        Mockito.when(repository.findById(any(String.class))).thenReturn(events());
        Mockito.when(patientRepository.findById(any(String.class))).thenReturn(Mono.just(patient));
        Mockito.when(agendaRepository.assignSchedule(any(String.class),any(String.class))).thenReturn(Mono.empty());
        Mockito.when(repository.saveEvent(any(AgendaDayScheduleAssigned.class))).thenReturn(Mono.just(eventRes));
        Mockito.doAnswer(i -> null).when(bus).publish(ArgumentMatchers.any(DomainEvent.class),eq(email));

        //Act
        Flux<DomainEvent> result = useCase.apply(Mono.just(command));

        StepVerifier.create(result)
                //Assert
                .expectNextMatches(event1 -> event1 instanceof AgendaDayScheduleAssigned &&
                        ((AgendaDayScheduleAssigned) event1).getAgendaId().equals(command.getAgendaId()) &&
                        ((AgendaDayScheduleAssigned) event1).getPatientId().equals(command.getPatientId()) &&
                        ((AgendaDayScheduleAssigned) event1).getAppointmentDate().equals(command.getAppointmentDate())
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