package co.com.sofka.model.patient.values;

import co.com.sofka.model.generic.ValueObject;

import java.util.Objects;

public class FullName implements ValueObject<String> {
    private String fullName;

    public FullName( String fullName) {
        if(fullName.length() <= 5){
            throw new IllegalArgumentException();
        }
        this.fullName = Objects.requireNonNull(fullName);
    }

    @Override
    public String value() {
        return fullName;
    }
}
