package com.broniec.rest.famework.validator;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

class TypeConstraint<O> implements ValidationRules<O> {

    private static final String DEFAULT_TYPE = "type";

    private final ConstraintViolationBuilder constraintViolationBuilder;
    private final Function<O, String> valueGetter;
    private Set<String> acceptableValues;

    public TypeConstraint(Function<O, String> valueGetter, Collection<String> acceptableValues) {
        this.acceptableValues = new LinkedHashSet<>(acceptableValues);
        this.valueGetter = valueGetter;
        this.constraintViolationBuilder = new ConstraintViolationBuilder(DEFAULT_TYPE);

    }

    @Override
    public Stream<ConstraintViolation> execute(O obj, ValidationContext context) {
        var type = valueGetter.apply(obj);
        if (acceptableValues.contains(type)) {
            return Stream.empty();
        } else {
            return Stream.of(constraintViolationBuilder.invalidType());
        }
    }
}
