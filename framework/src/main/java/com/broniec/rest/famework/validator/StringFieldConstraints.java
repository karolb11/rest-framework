package com.broniec.rest.famework.validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Stream;

public class StringFieldConstraints<O> implements ValidationRules<O> {

    private final Function<O, String> valueGetter;
    private final String fieldLabel;
    private final Collection<Constraint<O, String>> constraints;

    StringFieldConstraints(Function<O, String> valueGetter, String fieldLabel) {
        this.valueGetter = valueGetter;
        this.fieldLabel = fieldLabel;
        this.constraints = new ArrayList<>();
    }

    @Override
    public Stream<ConstraintViolation> execute(O obj, ValidationContext context) {
        var value = valueGetter.apply(obj);
        return constraints.stream().flatMap(constraint -> constraint.check(obj, context, value, fieldLabel));
    }

    public StringFieldConstraints<O> mandatory() {
        constraints.add(new MandatoryStringConstraint<>(fieldLabel));
        return this;
    }

    public StringFieldConstraints<O> minLength(int minLength) {
        constraints.add(new MinStringLengthConstraint<>(fieldLabel, minLength));
        return this;
    }

    public StringFieldConstraints<O> maxLength(int maxLength) {
        constraints.add(new MaxStringLengthConstraint<>(fieldLabel, maxLength));
        return this;
    }

    public StringFieldConstraints<O> startsWithCapital() {
        constraints.add(new CapitalStartingCharConstraint<>(fieldLabel));
        return this;
    }

}
