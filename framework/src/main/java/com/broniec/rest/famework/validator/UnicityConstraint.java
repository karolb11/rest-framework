package com.broniec.rest.famework.validator;

import java.util.function.Predicate;
import java.util.stream.Stream;

public class UnicityConstraint<O> implements ValidationRules<O> {

    private final ConstraintViolationBuilder constraintViolationBuilder;

    private Predicate<O> duplicationFinder;

    UnicityConstraint(Predicate<O> duplicationFinder) {
        this.constraintViolationBuilder = new ConstraintViolationBuilder();
        this.duplicationFinder = duplicationFinder;
    }

    UnicityConstraint(Predicate<O> duplicationFinder, String fieldLabel) {
        this.constraintViolationBuilder = new ConstraintViolationBuilder(fieldLabel);
        this.duplicationFinder = duplicationFinder;
    }

    @Override
    public Stream<ConstraintViolation> execute(O obj) {
        if (duplicationFinder.test(obj)) {
            return Stream.of(constraintViolationBuilder.objectAlreadyExists());
        }
        return Stream.empty();
    }

}
