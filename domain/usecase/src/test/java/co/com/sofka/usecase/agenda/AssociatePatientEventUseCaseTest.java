package co.com.sofka.usecase.agenda;

import co.com.sofka.model.agenda.events.AgendaCreated;
import co.com.sofka.model.agenda.events.AgendaDayScheduleAssigned;
import co.com.sofka.model.agenda.events.PatientAssociated;
import co.com.sofka.model.events.gateways.EventBus;
import co.com.sofka.model.generic.DomainEvent;
import co.com.sofka.model.patient.events.AppointmentAssociated;
import co.com.sofka.model.patient.events.PatientCreated;
import co.com.sofka.model.patient.values.Email;
import co.com.sofka.model.patient.values.FullName;
import co.com.sofka.model.patient.values.TypeId;
import co.com.sofka.usecase.gateways.AgendaRepository;
import co.com.sofka.usecase.gateways.DomainEventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

class AssociatePatientEventUseCaseTest {

    @Mock
    private DomainEventRepository repository;
    @Mock
    private AgendaRepository agendaRepository;

    @Mock
    private EventBus bus;

    private AssociatePatientEventUseCase useCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = new AssociatePatientEventUseCase(repository, agendaRepository,bus);
    }


    @Test
    void applyHappyPass() {

        String AGGREGATE_ID = "test";
        String agendaId = "agenda1";
        String patientId = "patient1";
        String appointmentDate = "2023/03/09 10:00";

        AgendaDayScheduleAssigned event = new AgendaDayScheduleAssigned(agendaId,patientId,appointmentDate);
        event.setAggregateRootId(AGGREGATE_ID);

        PatientAssociated patientAssociated = new PatientAssociated(agendaId,patientId);

        Mockito.when(repository.findById(any(String.class))).thenReturn(events());
        Mockito.when(agendaRepository.associatePatientId(any(String.class), any(String.class))).thenReturn(Mono.empty());
        Mockito.when(repository.saveEvent(any(PatientAssociated.class))).thenReturn(Mono.just(patientAssociated));
        Mockito.doAnswer(i -> null).when(bus).publish(ArgumentMatchers.any(DomainEvent.class),eq(event.type));

        //Act
        Flux<DomainEvent> result = useCase.apply(Mono.just(event));

        StepVerifier.create(result)
                //Assert
                .expectNextMatches(event1 -> event1 instanceof PatientAssociated &&
                        ((PatientAssociated) event1).getPatientId().equals(event.getPatientId()) &&
                        ((PatientAssociated) event1).getAgendaId().equals(event.getAgendaId())
                )
                .expectComplete()
                .verify();

    }

    private Flux<DomainEvent> events() {
        return Flux.just(new AgendaCreated(
                "2023/03/06 08:00","2023/03/10 16:00"));
    }
}