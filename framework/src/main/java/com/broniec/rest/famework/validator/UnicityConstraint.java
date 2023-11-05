package com.broniec.rest.famework.validator;

import java.util.function.Predicate;
import java.util.stream.Stream;

public class UnicityConstraint<T> implements ValidationRules<T> {

    private final ConstraintViolationBuilder constraintViolationBuilder;

    private Predicate<T> duplicationFinder;

    UnicityConstraint(Predicate<T> duplicationFinder) {
        this.constraintViolationBuilder = new ConstraintViolationBuilder();
        this.duplicationFinder = duplicationFinder;
    }

    UnicityConstraint(Predicate<T> duplicationFinder, String fieldLabel) {
        this.constraintViolationBuilder = new ConstraintViolationBuilder(fieldLabel);
        this.duplicationFinder = duplicationFinder;
    }

    @Override
    public Stream<ConstraintViolation> execute(T obj) {
        if (duplicationFinder.test(obj)) {
            return Stream.of(constraintViolationBuilder.objectAlreadyExists());
        }
        return Stream.empty();
    }

}
