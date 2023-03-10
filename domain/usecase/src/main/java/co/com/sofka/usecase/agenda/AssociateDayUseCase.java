package co.com.sofka.usecase.agenda;

import co.com.sofka.model.agenda.Agenda;
import co.com.sofka.model.agenda.values.AgendaId;
import co.com.sofka.model.agenda.values.DayName;
import co.com.sofka.model.agenda.values.Schedule;
import co.com.sofka.model.generic.DomainEvent;
import co.com.sofka.model.patient.Patient;
import co.com.sofka.model.patient.values.AppointmentDate;
import co.com.sofka.model.patient.values.PatientId;
import co.com.sofka.usecase.agenda.commands.AssociateDayCommand;
import co.com.sofka.usecase.gateways.DomainEventRepository;
import co.com.sofka.usecase.generic.UseCaseForCommand;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class AssociateDayUseCase extends UseCaseForCommand<AssociateDayCommand> {

    private final DomainEventRepository repository;

    public AssociateDayUseCase(DomainEventRepository repository) {
        this.repository = repository;
    }

    @Override
    public Flux<DomainEvent> apply(Mono<AssociateDayCommand> associateDayCommandMono) {
        return associateDayCommandMono.flatMapMany(command -> repository.findById(command.getAgendaId())
                .collectList()
                .flatMapIterable(events ->{
                    Agenda agenda = Agenda.from(AgendaId.of(command.getAgendaId()), events);

                    agenda.AssociateDay(new DayName(command.getDayName()),(List<Schedule>)command.getSchedules());

                    return agenda.getUncommittedChanges();
                }).map(event -> {
                    return event;
                }).flatMap(event ->{
                    return repository.saveEvent(event);
                }));
    }
}
