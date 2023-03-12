package co.com.sofka.mongo.AgendaRepository;



import co.com.sofka.mongo.data.DayDTO;
import co.com.sofka.mongo.data.ScheduleDTO;
import co.com.sofka.usecase.agenda.commands.AssociateDayCommand;
import co.com.sofka.usecase.agenda.commands.CreateAgendaCommand;
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
    public Mono<CreateAgendaCommand> createAgenda(CreateAgendaCommand createAgendaCommand) {

        var days = new ArrayList<Object>();
        var patients = new ArrayList<String>();
        patients.add(" ");

        createAgendaCommand.setDays(days);
        createAgendaCommand.setPatients(patients);
        return dto.save(createAgendaCommand);

    }

    @Override
    public Mono<CreateAgendaCommand> addDayAgenda(AssociateDayCommand associateDayCommand) {
        return dto.findById(associateDayCommand.getAgendaId())
                .map(patientCommand -> {

                    var day = new DayDTO(associateDayCommand.getDayId(),associateDayCommand.getDayName(),
                            (List<ScheduleDTO>) associateDayCommand.getSchedules());

                    patientCommand.getDays().add(day);
                    return dto.save(patientCommand);
                }).flatMap(res -> {return res;} );
    }

    @SneakyThrows
    @Override
    public Mono<Boolean> assignSchedule(String schedule,String id){

       LocalDateTime scheduleformat = dateformat(schedule);

        Mono<Boolean> res= dto.findById(id)
                .flatMap(agendadoc -> {
                    // Buscamos el día con dayName "Monday"
                    Optional<Object> optionalDay = agendadoc.getDays().stream()
                            .filter(day ->{
                                var dayDTO = (DayDTO) day;
                             return dayDTO.getDayName().equals("Monday");
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
                                dto.save(agendadoc);
                                return Mono.just(true);
                            }else {return Mono.just(false);}

                        }return Mono.just(false);
                    }
                    // Si no se encontró el día o el horario, retornamos la agenda original
                    return Mono.just(false);
                });


        return res;
    }

    @Override
    public Mono<CreateAgendaCommand> getAgendaByid(String id) {
        return dto.findById(id);
    }

    public static LocalDateTime dateformat(String dateString) throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date date = dateFormat.parse(dateString);
        Instant instant = date.toInstant();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return localDateTime;
    }

}
