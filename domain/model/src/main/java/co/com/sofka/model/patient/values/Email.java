package co.com.sofka.model.patient.values;

import co.com.sofka.model.generic.ValueObject;

import java.util.Objects;

public class Email implements ValueObject<String> {

    private String email;

    public Email( String email) {
        if(email.length() <= 5){
            throw new IllegalArgumentException();
        }
        this.email = Objects.requireNonNull(email);
    }

    @Override
    public String value() {
        return email;
    }
}
