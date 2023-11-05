package com.broniec.rest.famework.validator;

import java.time.LocalDate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface ValidationRules<T> {

    Stream<ConstraintViolation> execute(T obj);

    static <T> StringFieldConstraints<T> stringValidation(Function<T, String> getter, String fieldLabel) {
        return new StringFieldConstraints<>(getter, fieldLabel);
    }

    static <T> UnicityConstraint<T> unicityConstraint(Predicate<T> duplicationFinder) {
        return new UnicityConstraint<>(duplicationFinder);
    }

    static <T> UnicityConstraint<T> unicityConstraint(Predicate<T> duplicationFinder, String fieldLabel) {
        return new UnicityConstraint<>(duplicationFinder, fieldLabel);
    }

    static <T> LocalDateFieldConstraints<T> dateValidation(Function<T, LocalDate> getter, String fieldLabel) {
        return new LocalDateFieldConstraints<>(getter, fieldLabel);
    }

}
