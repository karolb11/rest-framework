package com.broniec.rest.famework.validator;

import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

class CapitalStartingCharConstraint<O> implements Constraint<O, String> {

    private final ConstraintViolationBuilder constraintViolationBuilder;

    CapitalStartingCharConstraint(String fieldLabel) {
        constraintViolationBuilder = new ConstraintViolationBuilder(fieldLabel);
    }

    @Override
    public Stream<ConstraintViolation> check(O validatedObj, ValidationContext context, String value, String fieldLabel) {
        if (StringUtils.isBlank(value)) {
            return Stream.empty();
        } else if (Character.isUpperCase(value.charAt(0))) {
            return Stream.empty();
        } else {
            return Stream.of(constraintViolationBuilder.mustStartWithCapitalLetter());
        }
    }
}
