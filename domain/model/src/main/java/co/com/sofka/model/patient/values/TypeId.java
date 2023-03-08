package co.com.sofka.model.patient.values;

import co.com.sofka.model.generic.ValueObject;

public class TypeId implements ValueObject<String> {

    private String typeId;

    public TypeId(String typeId) {
        this.typeId = typeId
        ;
    }

    @Override
    public String value() {
        return typeId;
    }
}
