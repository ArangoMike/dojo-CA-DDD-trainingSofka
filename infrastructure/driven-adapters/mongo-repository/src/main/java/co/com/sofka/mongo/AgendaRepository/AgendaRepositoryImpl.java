package co.com.sofka.mongo.AgendaRepository;


import co.com.sofka.mongo.data.DayDTO;
import co.com.sofka.mongo.data.ScheduleDTO;
import co.com.sofka.usecase.agenda.commands.AssociateDayCommand;
import co.com.sofka.usecase.agenda.commands.CreateAgendaCommand;
import co.com.sofka.usecase.agenda.commands.DisableScheduleDayCommand;
import co.com.sofka.usecase.gateways.AgendaRepository;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;



@Slf4j
@Component
@AllArgsConstructor
public class AgendaRepositoryImpl implements AgendaRepository {

    private MongoAgendaRepositoryAdapter dto;

    private MongoDBAgendaRepository repository;




    @Override
    public Mono<Void> createAgenda(CreateAgendaCommand createAgendaCommand) {

        var days = new ArrayList<>();
        var patients = new ArrayList<String>();
        patients.add("");

        createAgendaCommand.setDays(days);
        createAgendaCommand.setPatients(patients);
        return dto.save(createAgendaCommand)
                .then();

    }

    @Override
    public Mono<Void> addDayAgenda(AssociateDayCommand associateDayCommand) {
        return dto.findById(associateDayCommand.getAgendaId())
                .map(agendaCommand -> {

                    var day = new DayDTO(associateDayCommand.getDayId(),associateDayCommand.getDayName(),
                            (List<ScheduleDTO>) associateDayCommand.getSchedules());

                    agendaCommand.getDays().add(day);
                    return dto.save(agendaCommand);
                }).flatMap(agenda -> { return agenda;}).then();
    }

    @SneakyThrows
    @Override
    public Mono<Void> assignSchedule(String schedule,String id){

       LocalDateTime scheduleformat = dateformat(schedule);

        return dto.findById(id)
                .flatMap(agendadoc -> {
                    // Buscamos el día con dayName "Monday"
                    Optional<Object> optionalDay = agendadoc.getDays().stream()
                            .filter(day ->{
                                var dayDTO = (DayDTO) day;
                             return dayDTO.getDayName().equals(String.valueOf(scheduleformat.getDayOfWeek()));
                            })
                            .findFirst();
                    if (optionalDay.isPresent()) {
                        DayDTO day = (DayDTO) optionalDay.get();
                        // Buscamos el horario con schedule "08:00"
                        Optional<ScheduleDTO> optionalSchedule = day.getSchedules().stream()
                                .filter(schedule1 -> schedule1.getSchedule()
                                        .contains(String.valueOf(scheduleformat.getHour())))
                                .findFirst();
                        if (optionalSchedule.isPresent()) {
                            ScheduleDTO schedule2 = optionalSchedule.get();
                            // Modificamos el atributo "enable" del horario a false
                            if(schedule2.getEnable()) {
                                schedule2.setEnable(false);

                                // Guardamos la AgendaDocument modificada en la base de datos
                                dto.save(agendadoc).subscribe();

                                return Mono.just(true);
                            }
                        }return Mono.just(false);
                    }
                    // Si no se encontró el día o el horario, retornamos la agenda original
                    return Mono.just(false);
                }).then();
    }

    @Override
    public Mono<CreateAgendaCommand> getAgendaByid(String id) {
        return dto.findById(id);
    }

    @Override
    public Mono<Void> associatePatientId(String patientId, String id) {
        return dto.findById(id)
                .flatMap(agenda-> {
                    agenda.getPatients().add(patientId);
                    return dto.save(agenda);
                }).then();
    }

    @Override
    public Mono<Void> disableScheduleDay(DisableScheduleDayCommand disableScheduleDayCommand) {

     return dto.findById(disableScheduleDayCommand.getAgendaId())
                .flatMap(agenda -> {
                    Optional<Object> optionalDay = agenda.getDays().stream()
                            .filter(day -> {
                                var dayDTO = (DayDTO) day;
                                return dayDTO.getDayName().equals(disableScheduleDayCommand.getDayName());
                            })
                            .findFirst();
                    if (optionalDay.isPresent()) {
                        DayDTO day = (DayDTO) optionalDay.get();
                        // Buscamos el horario con schedule "08:00"
                        Optional<ScheduleDTO> optionalSchedule = day.getSchedules().stream()
                                .filter(schedule1 -> schedule1.getSchedule()
                                        .contains(String.valueOf(disableScheduleDayCommand.getSchedule())))
                                .findFirst();

                        if (optionalSchedule.isPresent()) {
                            ScheduleDTO schedule2 = optionalSchedule.get();

                            // Modificamos el atributo "enable" del horario a false
                            if (!schedule2.getEnable()) {
                                schedule2.setEnable(disableScheduleDayCommand.getEnable());
                                dto.save(agenda).subscribe();
                                return Mono.just("Horario modificado exitosamente");
                            }
                        }return Mono.just("No se logró modificar el horario verifique que si este ocupado.");
                    }
                    return Mono.just("No se logró modificar el horario verifique que si este ocupado.");
    }).then();
    }


    public static LocalDateTime dateformat(String dateString) throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date date = dateFormat.parse(dateString);
        Instant instant = date.toInstant();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return localDateTime;
    }

}
