package co.com.sofka.mongo.PatientRepository;


import co.com.sofka.mongo.data.AppointmentDTO;
import co.com.sofka.usecase.gateways.PatientRepository;
import co.com.sofka.usecase.patient.commands.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Iterator;



@Slf4j
@Component
@AllArgsConstructor
public class PatientRepositoryImpl implements PatientRepository {

    private  MongoRepositoryAdapter dto;


    @Override
    public Mono<Void> createPatient(CreatePatientCommand patientCreated) {
        var appointments = new ArrayList<Object>();
        patientCreated.setAppointments(appointments);
        return dto.save(patientCreated).map(patientCommand ->{
            log.info("aa"+patientCommand); return patientCommand;}).then();
    }

    @Override
    public Mono<Void> modifyEnablePatient(ModifyEnablePatientCommand modifyEnablePatientCommand) {
       return dto.findById(modifyEnablePatientCommand.getPatientId())
                .map(createPatientCommand -> {
                    createPatientCommand.setEnable(modifyEnablePatientCommand.getEnable());
                return dto.save(createPatientCommand).subscribe();
                }).then();
    }

    @Override
    public Mono<Void> modifyEmailPatient(ModifyEmailPatientCommand modifyEmailPatientCommand) {
        return dto.findById(modifyEmailPatientCommand.getPatientId())
                .map(patientCommand -> {
                    patientCommand.setEmail(modifyEmailPatientCommand.getEmail());

                    return dto.save(patientCommand).subscribe();
                }).then();
    }

    @Override
    public Mono<Void> addAppointmentPatient(AssociateAppointmentCommand associateAppointmentCommand) {

        return dto.findById(associateAppointmentCommand.getPatientId())
                .map(patientCommand -> {

                      var appointment = new AppointmentDTO(associateAppointmentCommand.getAppointmentId(),
                              associateAppointmentCommand.getAppointmentDate(),"");

                    patientCommand.getAppointments().add(appointment);
                    return dto.save(patientCommand).subscribe();
                }).then();
    }

    @Override
    public Mono<String> findEmailById(String id) {
        return dto.findById(id)
                .map(patient -> patient.getEmail());
    }

    @Override
    public Mono<CreatePatientCommand> findById(String id) {
        return dto.findById(id);
    }

    @Override
    public Mono<Void> assignMedicalCheckupByAppointmentById(AssignMedicalCheckupAppointmentCommand assignMedicalCheckupAppointmentCommand) {

        return dto.findById(assignMedicalCheckupAppointmentCommand.getPatientId())
                .map(patientCommand -> {
                    Iterator<Object> iterator = patientCommand.getAppointments().iterator();
                    while(iterator.hasNext()) {
                        var appointment = iterator.next();
                        if(appointment instanceof AppointmentDTO) {
                            AppointmentDTO appointmentDTO = (AppointmentDTO)appointment;
                            if(appointmentDTO.getAppointmentId().equals(assignMedicalCheckupAppointmentCommand.getAppointmentId())){
                                iterator.remove(); // Remove appointment using the iterator
                                appointmentDTO.setMedicalCheckup(assignMedicalCheckupAppointmentCommand.getMedicalCheckup());
                                patientCommand.getAppointments().add(appointmentDTO);
                                break;
                            }
                        }
                    }
                    return dto.save(patientCommand).subscribe();
                }).then();
    }

}
