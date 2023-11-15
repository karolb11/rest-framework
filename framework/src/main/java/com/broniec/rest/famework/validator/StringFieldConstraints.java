package com.broniec.rest.famework.validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Stream;

public class StringFieldConstraints<T> implements ValidationRules<T> {

    private final Function<T, String> valueGetter;
    private final String fieldLabel;
    private final Collection<Constraint<String>> constraints;

    StringFieldConstraints(Function<T, String> valueGetter, String fieldLabel) {
        this.valueGetter = valueGetter;
        this.fieldLabel = fieldLabel;
        this.constraints = new ArrayList<>();
    }

    @Override
    public Stream<ConstraintViolation> execute(T obj) {
        var value = valueGetter.apply(obj);
        return constraints.stream().flatMap(constraint -> constraint.check(value, fieldLabel));
    }

    public StringFieldConstraints<T> mandatory() {
        constraints.add(new MandatoryStringConstraint(fieldLabel));
        return this;
    }

    public StringFieldConstraints<T> minLength(int minLength) {
        constraints.add(new MinStringLengthConstraint(fieldLabel, minLength));
        return this;
    }

    public StringFieldConstraints<T> maxLength(int maxLength) {
        constraints.add(new MaxStringLengthConstraint(fieldLabel, maxLength));
        return this;
    }

}
