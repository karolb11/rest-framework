package com.broniec.rest.famework.validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class ComposedListValidator<T, K> implements ValidationRules<T> {

    private final Function<T, List<K>> valueGetter;
    private final String fieldLabel;
    private final Validator<K> validator;
    private final Collection<Constraint<List<K>>> constraints;

    ComposedListValidator(Function<T, List<K>> valueGetter, String fieldLabel, ValidationConfig<K> validationConfig) {
        this.valueGetter = valueGetter;
        this.fieldLabel = fieldLabel;
        this.validator = new Validator<>(validationConfig);
        this.constraints = new ArrayList<>();
        constraints.add(new ComposedListConstraint<K>(fieldLabel, validator));
    }


    @Override
    public Stream<ConstraintViolation> execute(T obj) {
        var value = valueGetter.apply(obj);
        return constraints.stream().flatMap(constraint -> constraint.check(value, fieldLabel));
    }

    public ComposedListValidator<T, K> mandatory() {
        constraints.add(new MandatoryObjectConstraint<>(fieldLabel));
        return this;
    }

}
