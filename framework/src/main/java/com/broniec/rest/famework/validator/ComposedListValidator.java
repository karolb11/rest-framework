package com.broniec.rest.famework.validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class ComposedListValidator<T, K> implements ValidationRules<T> {

    private final Function<T, List<K>> valueGetter;
    private final String fieldLabel;
    private final Validator<K> validator;
    private final Collection<Constraint<List<?>>> constraints;

    ComposedListValidator(Function<T, List<K>> valueGetter, String fieldLabel, ValidationConfig<K> validationConfig) {
        this.valueGetter = valueGetter;
        this.fieldLabel = fieldLabel;
        this.validator = new Validator<>(validationConfig);
        this.constraints = new ArrayList<>();
    }


    @Override
    public Stream<ConstraintViolation> execute(T obj) {
        var resultBuilder = Stream.<ConstraintViolation>builder();
        var value = valueGetter.apply(obj);

        constraints.stream().flatMap(constraint -> constraint.check(value, fieldLabel))
                .forEach(resultBuilder::add);

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

    public ComposedListValidator<T, K> mandatory() {
        constraints.add(new MandatoryObjectConstraint<>(fieldLabel));
        return this;
    }

}
