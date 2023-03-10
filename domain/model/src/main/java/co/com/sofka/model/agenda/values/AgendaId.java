package co.com.sofka.model.agenda.values;

import co.com.sofka.model.generic.Identity;
import co.com.sofka.model.patient.values.PatientId;

public class AgendaId extends Identity {

    private AgendaId(String uuid) {
        super(uuid);
    }

    public AgendaId() {
    }

    public static AgendaId of(String uuid){
        return new AgendaId(uuid);
    }
}
