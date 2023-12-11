package com.broniec.rest.famework.validator;

import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Objects.nonNull;

class IdentityConstraint<O> implements ValidationRules<O> {

    private final Function<O, ?> valueGetter;
    private final ConstraintViolationBuilder constraintViolationBuilder;

    IdentityConstraint(Function<O, ?> valueGetter, String fieldLabel) {
        this.valueGetter = valueGetter;
        constraintViolationBuilder = new ConstraintViolationBuilder(fieldLabel);
    }

    @Override
    public Stream<ConstraintViolation> execute(O obj, ValidationContext context) {
        var value = valueGetter.apply(obj);
        if (context.isCreate() && nonNull(value)) {
            return Stream.of(constraintViolationBuilder.notApplicableForCreateRequest());
        }
        return Stream.empty();
    }

}
