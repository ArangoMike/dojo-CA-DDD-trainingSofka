package co.com.sofka.mongo.PatientRepository;


import co.com.sofka.mongo.data.AppointmentDTO;
import co.com.sofka.usecase.gateways.PatientRepository;
import co.com.sofka.usecase.patient.commands.AssociateAppointmentCommand;
import co.com.sofka.usecase.patient.commands.CreatePatientCommand;
import co.com.sofka.usecase.patient.commands.ModifyEmailPatientCommand;
import co.com.sofka.usecase.patient.commands.ModifyEnablePatientCommand;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Component
@AllArgsConstructor
public class PatientRepositoryImpl implements PatientRepository {

    private  MongoRepositoryAdapter dto;

    private  MongoDBRepository repository;


    @Override
    public Mono<CreatePatientCommand> createPatient(CreatePatientCommand patientCreated) {
        var appointments = new ArrayList<Object>();
        patientCreated.setAppointments(appointments);
        return dto.save(patientCreated);
    }

    @Override
    public Mono<CreatePatientCommand> modifyEnablePatient(ModifyEnablePatientCommand modifyEnablePatientCommand) {
       return dto.findById(modifyEnablePatientCommand.getPatientId())
                .map(createPatientCommand -> {
                    createPatientCommand.setEnable(modifyEnablePatientCommand.getEnable());
                return dto.save(createPatientCommand);
                }).flatMap(res -> {return res;} );
    }

    @Override
    public Mono<CreatePatientCommand> modifyEmailPatient(ModifyEmailPatientCommand modifyEmailPatientCommand) {
        return dto.findById(modifyEmailPatientCommand.getPatientId())
                .map(patientCommand -> {
                    patientCommand.setEmail(modifyEmailPatientCommand.getEmail());

                    return dto.save(patientCommand);
                }).flatMap(res -> {return res;} );
    }

    @Override
    public Mono<CreatePatientCommand> addAppointmentPatient(AssociateAppointmentCommand associateAppointmentCommand) {

        return dto.findById(associateAppointmentCommand.getPatientId())
                .map(patientCommand -> {
                    List<Object> appointments = new ArrayList<>();
                      var appointment = new AppointmentDTO(associateAppointmentCommand.getAppointmentId(),
                              associateAppointmentCommand.getAppointmentDate());
                    appointments.add(appointment);
                    patientCommand.setAppointments(appointments);
                    return dto.save(patientCommand);
                }).flatMap(res -> {return res;} );
    }

    @Override
    public Mono<String> findEmailById(String id) {
        return dto.findById(id)
                .map(patient -> patient.getEmail());
    }

}
