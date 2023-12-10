package com.broniec.rest.famework.validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class ListFieldConstraints<O> implements ValidationRules<O> {

    private final Function<O, List<?>> valueGetter;
    private final String fieldLabel;
    private final Collection<Constraint<O, List<?>>> constraints;

    public ListFieldConstraints(Function<O, List<?>> valueGetter, String fieldLabel) {
        this.valueGetter = valueGetter;
        this.fieldLabel = fieldLabel;
        this.constraints = new ArrayList<>();
    }

    @Override
    public Stream<ConstraintViolation> execute(O obj, ValidationContext context) {
        var value = valueGetter.apply(obj);
        return constraints.stream().flatMap(constraint -> constraint.check(obj, context, value, fieldLabel));
    }

    public ListFieldConstraints<O> mandatory() {
        constraints.add(new MandatoryObjectConstraint<>(fieldLabel));
        return this;
    }

}
