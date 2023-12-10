package com.broniec.rest.famework.validator;

import java.time.LocalDate;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

class DateBeforeConstraint<O> implements Constraint<O, LocalDate> {

    private final ConstraintViolationBuilder constraintViolationBuilder;
    private final Function<O, LocalDate> beforeGetter;

    DateBeforeConstraint(String fieldLabel, Function<O, LocalDate> beforeGetter) {
        this.constraintViolationBuilder = new ConstraintViolationBuilder(fieldLabel);
        this.beforeGetter = beforeGetter;
    }

    @Override
    public Stream<ConstraintViolation> check(O validatedObj, ValidationContext context, LocalDate value, String fieldLabel) {
        if (isNull(value)) {
            return Stream.empty();
        }
        var dateThatMustBeAfter = beforeGetter.apply(validatedObj);
        if (nonNull(dateThatMustBeAfter) && value.isAfter(dateThatMustBeAfter)) {
            return Stream.of(constraintViolationBuilder.dateMustBeBefore(dateThatMustBeAfter));
        }
        return Stream.empty();
    }

}
