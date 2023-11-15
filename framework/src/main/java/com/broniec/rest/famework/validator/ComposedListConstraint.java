package com.broniec.rest.famework.validator;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

class ComposedListConstraint<O, F> implements Constraint<O, List<F>> {

    private final Validator<F> validator;

    ComposedListConstraint(String fieldLabel, Validator<F> validator) {
        this.validator = validator;
    }


    @Override
    public Stream<ConstraintViolation> check(O validatedObj, List<F> value, String fieldLabel) {
        var resultBuilder = Stream.<ConstraintViolation>builder();

        if (nonNull(value)) {
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
        }

        return resultBuilder.build();
    }
}
