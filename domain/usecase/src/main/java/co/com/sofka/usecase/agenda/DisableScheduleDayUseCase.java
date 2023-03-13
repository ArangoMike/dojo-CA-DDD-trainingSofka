package co.com.sofka.usecase.agenda;

import co.com.sofka.model.agenda.Agenda;
import co.com.sofka.model.agenda.values.AgendaId;
import co.com.sofka.model.agenda.values.DayName;
import co.com.sofka.model.generic.DomainEvent;
import co.com.sofka.model.patient.values.PatientId;
import co.com.sofka.usecase.agenda.commands.DisableScheduleDayCommand;
import co.com.sofka.usecase.gateways.AgendaRepository;
import co.com.sofka.usecase.gateways.DomainEventRepository;
import co.com.sofka.usecase.generic.UseCaseForCommand;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class DisableScheduleDayUseCase extends UseCaseForCommand<DisableScheduleDayCommand> {

    private final DomainEventRepository repository;
    private final AgendaRepository agendaRepository;


    public DisableScheduleDayUseCase(DomainEventRepository repository, AgendaRepository agendaRepository) {
        this.repository = repository;
        this.agendaRepository = agendaRepository;
    }

    @Override
    public Flux<DomainEvent> apply(Mono<DisableScheduleDayCommand> disableScheduleDayCommandMono) {
        return disableScheduleDayCommandMono.flatMapMany(command -> repository.findById(command.getAgendaId())
                .collectList()
                .flatMapIterable(events ->{
                    Agenda agenda = Agenda.from(AgendaId.of(command.getAgendaId()), events);

                    agenda.DisableScheduleDay(PatientId.of(command.getPatientId()),
                            AgendaId.of(command.getAgendaId()),new DayName(command.getDayName()),
                            command.getSchedule(),command.getEnable());
                    agendaRepository.disableScheduleDay(command).subscribe().isDisposed();

                    return agenda.getUncommittedChanges();
                }).map(event -> {
                    return event;
                }).flatMap(event ->{
                    return repository.saveEvent(event);
                }));
    }

}
