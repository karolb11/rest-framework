package com.broniec.rest.famework.validator;

import java.util.Objects;
import java.util.stream.Stream;

class MaxStringLengthConstraint<O> implements Constraint<O, String> {

    private final ConstraintViolationBuilder constraintViolationBuilder;
    private final int size;

    MaxStringLengthConstraint(String fieldLabel, int size) {
        constraintViolationBuilder = new ConstraintViolationBuilder(fieldLabel);
        this.size = size;
    }

    @Override
    public Stream<ConstraintViolation> check(O validatedObj, String value, String fieldLabel) {
        if (Objects.isNull(value)) {
            return Stream.empty();
        }
        if (value.length() <= size) {
            return Stream.empty();
        }
        return Stream.of(constraintViolationBuilder.maxLength(size));
    }
}
