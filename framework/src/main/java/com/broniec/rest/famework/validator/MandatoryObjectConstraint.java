package com.broniec.rest.famework.validator;

import java.util.stream.Stream;

import static java.util.Objects.isNull;

class MandatoryObjectConstraint<O, F> implements Constraint<O, F> {

    private final ConstraintViolationBuilder constraintViolationBuilder;

    MandatoryObjectConstraint(String fieldLabel) {
        constraintViolationBuilder = new ConstraintViolationBuilder(fieldLabel);
    }

    @Override
    public Stream<ConstraintViolation> check(O validatedObj, ValidationContext context, F value, String fieldLabel) {
        if (isNull(value)) {
            return Stream.of(constraintViolationBuilder.mandatoryField());
        } else {
            return Stream.empty();
        }
    }
}
