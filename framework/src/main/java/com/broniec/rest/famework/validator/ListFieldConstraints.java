package com.broniec.rest.famework.validator;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Objects.isNull;

public class ListFieldConstraints<T> implements ValidationRules<T> {

    private final ConstraintViolationBuilder constraintViolationBuilder;

    private final Function<T, List<?>> valueGetter;

    private boolean mandatory;

    public ListFieldConstraints(Function<T, List<?>> valueGetter, String fieldLabel) {
        this.constraintViolationBuilder = new ConstraintViolationBuilder(fieldLabel);
        this.valueGetter = valueGetter;
    }

    @Override
    public Stream<ConstraintViolation> execute(T obj) {
        var resultBuilder = Stream.<ConstraintViolation>builder();
        var value = valueGetter.apply(obj);

        if (isNull(value)) {
            if (mandatory) {
                resultBuilder.add(constraintViolationBuilder.mandatoryField());
            }
            return resultBuilder.build();
        }

        return resultBuilder.build();
    }

    public ListFieldConstraints<T> mandatory() {
        mandatory = true;
        return this;
    }

}
