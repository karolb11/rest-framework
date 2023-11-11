package com.broniec.rest.famework.validator;

import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Objects.isNull;

public class ComposedValidator<T, K> implements ValidationRules<T> {

    private final Function<T, K> valueGetter;
    private final String fieldLabel;
    private final Validator<K> validator;

    ComposedValidator(Function<T, K> valueGetter, String fieldLabel, ValidationConfig<K> validationConfig) {
        this.valueGetter = valueGetter;
        this.fieldLabel = fieldLabel;
        this.validator = new Validator<>(validationConfig);
    }


    @Override
    public Stream<ConstraintViolation> execute(T obj) {
        var value = valueGetter.apply(obj);

        if (isNull(value)) {
            return Stream.empty();
        }

        return validator.validate(value).stream().map(violation -> {
            var field = isNull(violation.field()) ? fieldLabel : fieldLabel + "." + violation.field();
            return new ConstraintViolation(field, violation.message());
        });
    }

}
