package com.broniec.rest.famework.validator;

import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Objects.isNull;

public class ComposedValidator<O, F> implements ValidationRules<O> {

    private final Function<O, F> valueGetter;
    private final String fieldLabel;
    private final Validator<F> validator;

    ComposedValidator(Function<O, F> valueGetter, String fieldLabel, ValidationConfig<F> validationConfig) {
        this.valueGetter = valueGetter;
        this.fieldLabel = fieldLabel;
        this.validator = new Validator<>(validationConfig);
    }


    @Override
    public Stream<ConstraintViolation> execute(O obj) {
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
