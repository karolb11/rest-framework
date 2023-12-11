package com.broniec.rest.famework.validator;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public interface ValidationRules<O> {

    Stream<ConstraintViolation> execute(O obj, ValidationContext context);

    static <T> ValidationRules<T> identityConstraint(Function<T, ?> valueGetter, String filedLabel) {
        return new IdentityConstraint<>(valueGetter, filedLabel);
    }

    static <T> StringFieldConstraints<T> stringValidation(Function<T, String> getter, String fieldLabel) {
        return new StringFieldConstraints<>(getter, fieldLabel);
    }

    static <T> UnicityConstraint<T> unicityConstraint(ValidationPredicate<T> duplicationFinder) {
        return new UnicityConstraint<>(duplicationFinder);
    }

    static <T> UnicityConstraint<T> unicityConstraint(ValidationPredicate<T> duplicationFinder, String fieldLabel) {
        return new UnicityConstraint<>(duplicationFinder, fieldLabel);
    }

    static <T> LocalDateFieldConstraints<T> dateValidation(Function<T, LocalDate> getter, String fieldLabel) {
        return new LocalDateFieldConstraints<>(getter, fieldLabel);
    }

    static <T> ListFieldConstraints<T> listValidator(Function<T, List<?>> getter, String fieldLabel) {
        return new ListFieldConstraints<>(getter, fieldLabel);
    }

    static <T, K> ComposedValidator<T, K> composedValidator(Function<T, K> valueGetter, String fieldLabel, ValidationConfig<K> config) {
        return new ComposedValidator<>(valueGetter, fieldLabel, config);
    }

    static <T, K> ComposedListValidator<T, K> composedListValidator(Function<T, List<K>> valueGetter, String fieldLabel, ValidationConfig<K> config) {
        return new ComposedListValidator<>(valueGetter, fieldLabel, config);
    }

}
