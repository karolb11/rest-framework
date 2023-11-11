package com.broniec.rest.famework.validator;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

public class StringFieldConstraints<T> implements ValidationRules<T> {

    private final ConstraintViolationBuilder constraintViolationBuilder;
    private final Function<T, String> getter;
    private boolean mandatory;
    private int minLength;
    private int maxLength;

    StringFieldConstraints(Function<T, String> getter, String fieldLabel) {
        this.constraintViolationBuilder = new ConstraintViolationBuilder(fieldLabel);
        this.getter = getter;
        this.maxLength = Integer.MAX_VALUE;
    }

    @Override
    public Stream<ConstraintViolation> execute(T obj) {
        var resultBuilder = Stream.<ConstraintViolation>builder();
        var value = Optional.ofNullable(getter.apply(obj)).orElse("");
        if (mandatory && StringUtils.isBlank(value)) {
            resultBuilder.add(constraintViolationBuilder.mandatoryField());
        }
        if (value.length() < minLength) {
            resultBuilder.add(constraintViolationBuilder.minLength(minLength));
        }
        if (value.length() > maxLength) {
            resultBuilder.add(constraintViolationBuilder.maxLength(maxLength));
        }
        return resultBuilder.build();
    }

    public StringFieldConstraints<T> mandatory() {
        mandatory = true;
        return this;
    }

    public StringFieldConstraints<T> minLength(int minLength) {
        this.minLength = minLength;
        return this;
    }

    public StringFieldConstraints<T> maxLength(int maxLength) {
        this.maxLength = maxLength;
        return this;
    }

}
