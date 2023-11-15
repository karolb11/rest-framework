package com.broniec.rest.famework.validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class ListFieldConstraints<T> implements ValidationRules<T> {

    private final Function<T, List<?>> valueGetter;
    private final String fieldLabel;
    private final Collection<Constraint<List<?>>> constraints;

    public ListFieldConstraints(Function<T, List<?>> valueGetter, String fieldLabel) {
        this.valueGetter = valueGetter;
        this.fieldLabel = fieldLabel;
        this.constraints = new ArrayList<>();
    }

    @Override
    public Stream<ConstraintViolation> execute(T obj) {
        var value = valueGetter.apply(obj);
        return constraints.stream().flatMap(constraint -> constraint.check(value, fieldLabel));
    }

    public ListFieldConstraints<T> mandatory() {
        constraints.add(new MandatoryObjectConstraint<>(fieldLabel));
        return this;
    }

}
