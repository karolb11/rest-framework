package com.broniec.rest.famework.validator;

import java.util.Objects;
import java.util.stream.Stream;

class MaxStringLengthConstraint implements Constraint<String> {

    private final ConstraintViolationBuilder constraintViolationBuilder;
    private final int size;

    MaxStringLengthConstraint(String fieldLabel, int size) {
        constraintViolationBuilder = new ConstraintViolationBuilder(fieldLabel);
        this.size = size;
    }

    @Override
    public Stream<ConstraintViolation> check(String value, String fieldLabel) {
        if (Objects.isNull(value)) {
            return Stream.empty();
        }
        if (value.length() <= size) {
            return Stream.empty();
        }
        return Stream.of(constraintViolationBuilder.maxLength(size));
    }
}
