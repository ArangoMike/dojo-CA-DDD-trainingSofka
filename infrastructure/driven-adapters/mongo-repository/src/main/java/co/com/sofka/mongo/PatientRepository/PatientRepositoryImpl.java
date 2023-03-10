package co.com.sofka.mongo.PatientRepository;


import co.com.sofka.model.patient.entities.Appointment;
import co.com.sofka.model.patient.events.AppointmentAssociated;
import co.com.sofka.model.patient.values.AppointmentId;
import co.com.sofka.mongo.data.PatientDocument;
import co.com.sofka.usecase.gateways.PatientRepository;
import co.com.sofka.usecase.patient.commands.AssociateAppointmentCommand;
import co.com.sofka.usecase.patient.commands.CreatePatientCommand;
import co.com.sofka.usecase.patient.commands.ModifyEnableCommand;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


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
    public Mono<CreatePatientCommand> updateEnablePacient(ModifyEnableCommand modifyEnableCommand) {
       return dto.findById(modifyEnableCommand.getPatientId())
                .map(createPatientCommand -> {createPatientCommand.setEnable(modifyEnableCommand.getEnable());
                return dto.save(createPatientCommand);
                }).flatMap(res -> {return res;} );
    }

    @Override
    public Mono<CreatePatientCommand> addAppointmentPacient(AssociateAppointmentCommand associateAppointmentCommand) {

        return dto.findById(associateAppointmentCommand.getPatientId())
                .map(patientCommand -> {
                    List<Object> appointments = new ArrayList<>();

                    appointments.add(associateAppointmentCommand);
                    patientCommand.setAppointments(appointments);
                    log.info(patientCommand.getAppointments().toString()+"entramos");
                    return dto.save(patientCommand);
                }).flatMap(res -> {return res;} );
    }

}
