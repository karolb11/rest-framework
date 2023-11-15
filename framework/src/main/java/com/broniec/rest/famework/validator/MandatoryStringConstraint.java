package com.broniec.rest.famework.validator;

import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

class MandatoryStringConstraint implements Constraint<String> {

    private final ConstraintViolationBuilder constraintViolationBuilder;

    MandatoryStringConstraint(String fieldLabel) {
        constraintViolationBuilder = new ConstraintViolationBuilder(fieldLabel);
    }

    @Override
    public Stream<ConstraintViolation> check(String value, String fieldLabel) {
        if (StringUtils.isBlank(value)) {
            return Stream.of(constraintViolationBuilder.mandatoryField());
        } else {
            return Stream.empty();
        }
    }
}
