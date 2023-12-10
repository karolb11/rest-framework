package com.broniec.rest.famework.validator;

import java.util.stream.Stream;

public class UnicityConstraint<O> implements ValidationRules<O> {

    private final ConstraintViolationBuilder constraintViolationBuilder;

    private ValidationPredicate<O> duplicationFinder;

    UnicityConstraint(ValidationPredicate<O> duplicationFinder) {
        this.constraintViolationBuilder = new ConstraintViolationBuilder();
        this.duplicationFinder = duplicationFinder;
    }

    UnicityConstraint(ValidationPredicate<O> duplicationFinder, String fieldLabel) {
        this.constraintViolationBuilder = new ConstraintViolationBuilder(fieldLabel);
        this.duplicationFinder = duplicationFinder;
    }

    @Override
    public Stream<ConstraintViolation> execute(O obj, ValidationContext context) {
        if (duplicationFinder.test(obj, context)) {
            return Stream.of(constraintViolationBuilder.objectAlreadyExists());
        }
        return Stream.empty();
    }

}
