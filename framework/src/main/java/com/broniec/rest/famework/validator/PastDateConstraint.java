package com.broniec.rest.famework.validator;

import java.time.LocalDate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.Objects.isNull;

class PastDateConstraint<O> implements Constraint<O, LocalDate> {

    private final ConstraintViolationBuilder constraintViolationBuilder;
    private final Supplier<LocalDate> currentDateSupplier;

    PastDateConstraint(String fieldLabel, Supplier<LocalDate> currentDateSupplier) {
        this.constraintViolationBuilder = new ConstraintViolationBuilder(fieldLabel);
        this.currentDateSupplier = currentDateSupplier;
    }

    @Override
    public Stream<ConstraintViolation> check(O validatedObj, ValidationContext context, LocalDate value, String fieldLabel) {
        if (isNull(value)) {
            return Stream.empty();
        }
        var currentDate = currentDateSupplier.get();
        if (value.isAfter(currentDate)) {
            return Stream.of(constraintViolationBuilder.dateMustBePast());
        }
        return Stream.empty();
    }

}
