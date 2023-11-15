package com.broniec.rest.famework.validator;

import java.util.stream.Stream;

import static java.util.Objects.isNull;

class MandatoryObjectConstraint<T> implements Constraint<T> {

    private final ConstraintViolationBuilder constraintViolationBuilder;

    MandatoryObjectConstraint(String fieldLabel) {
        constraintViolationBuilder = new ConstraintViolationBuilder(fieldLabel);
    }

    @Override
    public Stream<ConstraintViolation> check(T value, String fieldLabel) {
        if (isNull(value)) {
            return Stream.of(constraintViolationBuilder.mandatoryField());
        } else {
            return Stream.empty();
        }
    }
}
