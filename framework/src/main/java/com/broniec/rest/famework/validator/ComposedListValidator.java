package com.broniec.rest.famework.validator;

import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;

public class ComposedListValidator<T, K> implements ValidationRules<T> {

    private final ConstraintViolationBuilder constraintViolationBuilder;

    private final Function<T, List<K>> valueGetter;
    private final String fieldLabel;
    private final Validator<K> validator;
    private boolean mandatory;

    ComposedListValidator(Function<T, List<K>> valueGetter, String fieldLabel, ValidationConfig<K> validationConfig) {
        this.constraintViolationBuilder = new ConstraintViolationBuilder(fieldLabel);
        this.valueGetter = valueGetter;
        this.fieldLabel = fieldLabel;
        this.validator = new Validator<>(validationConfig);
    }


    @Override
    public Stream<ConstraintViolation> execute(T obj) {
        var resultBuilder = Stream.<ConstraintViolation>builder();
        var value = valueGetter.apply(obj);

        if (isNull(value)) {
            if (mandatory) {
                resultBuilder.add(constraintViolationBuilder.mandatoryField());
            }
            return resultBuilder.build();
        }

        IntStream.range(0, value.size()).forEach(i -> {
            validator.validate(value.get(i)).stream().map(violation -> {
                String field;
                if (isNull(violation.field())) {
                    field = fieldLabel + "[%s]".formatted(i);
                } else {
                    field = fieldLabel + "[%s]".formatted(i) + "." + violation.field();
                }
                return new ConstraintViolation(field, violation.message());
            }).forEach(resultBuilder::add);
        });

        return resultBuilder.build();
    }

    public ComposedListValidator<T, K> mandatory() {
        this.mandatory = true;
        return this;
    }

}
