package co.com.sofka.model.generic;

import java.io.Serializable;

public interface ValueObject<T> extends Serializable {
    T value();
}
