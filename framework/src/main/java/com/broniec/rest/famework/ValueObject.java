package com.broniec.rest.famework;

import static java.util.Objects.isNull;

public class ValueObject<T> {

    private final T value;

    public ValueObject(T value) {
        if (isNull(value)) {
            throw new IllegalArgumentException("value must not be null");
        }
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ValueObject<?> that = (ValueObject<?>) o;

        if (!value.equals(that.value)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
