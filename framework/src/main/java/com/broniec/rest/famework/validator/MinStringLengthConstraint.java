package com.broniec.rest.famework.validator;

import java.util.Objects;
import java.util.stream.Stream;

class MinStringLengthConstraint implements Constraint<String> {

    private final ConstraintViolationBuilder constraintViolationBuilder;
    private final int size;

    MinStringLengthConstraint(String fieldLabel, int size) {
        constraintViolationBuilder = new ConstraintViolationBuilder(fieldLabel);
        this.size = size;
    }

    @Override
    public Stream<ConstraintViolation> check(String value, String fieldLabel) {
        if (Objects.isNull(value)) {
            return Stream.empty();
        }
        if (value.length() >= size) {
            return Stream.empty();
        }
        return Stream.of(constraintViolationBuilder.minLength(size));
    }
}
