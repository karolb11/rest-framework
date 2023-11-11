package com.broniec.rest.demo;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import static java.util.Objects.isNull;

@Getter
@EqualsAndHashCode
public class ValueObject<T> {

    private final T value;

    public ValueObject(T value) {
        if (isNull(value)) {
            throw new IllegalArgumentException("value must not be null");
        }
        this.value = value;
    }
}
