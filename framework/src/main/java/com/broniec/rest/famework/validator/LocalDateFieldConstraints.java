package com.broniec.rest.famework.validator;

import java.time.LocalDate;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class LocalDateFieldConstraints<T> implements ValidationRules<T> {

    private final ConstraintViolationBuilder constraintViolationBuilder;

    private final Function<T, LocalDate> valueGetter;
    private Function<T, LocalDate> beforeGetter;
    private Supplier<LocalDate> currentDateSupplier;
    private boolean mustBePast;

    private boolean mandatory;

    public LocalDateFieldConstraints(Function<T, LocalDate> valueGetter, String fieldLabel) {
        this.constraintViolationBuilder = new ConstraintViolationBuilder(fieldLabel);
        this.valueGetter = valueGetter;
    }

    @Override
    public Stream<ConstraintViolation> execute(T obj) {
        var resultBuilder = Stream.<ConstraintViolation>builder();

        var value = valueGetter.apply(obj);


        if (isNull(value)) {
            if (mandatory) {
                resultBuilder.add(constraintViolationBuilder.mandatoryField());
            }
            return resultBuilder.build();
        }

        if (nonNull(beforeGetter)) {
            var dateThatMustBeAfter = beforeGetter.apply(obj);
            if (nonNull(dateThatMustBeAfter) && value.isAfter(dateThatMustBeAfter)) {
                resultBuilder.add(constraintViolationBuilder.dateMustBeBefore(dateThatMustBeAfter));
            }
        }

        if (mustBePast) {
            var currentDate = currentDateSupplier.get();
            if (value.isAfter(currentDate)) {
                resultBuilder.add(constraintViolationBuilder.dateMustBePast());
            }
        }

        return resultBuilder.build();
    }

    public LocalDateFieldConstraints<T> mandatory() {
        this.mandatory = true;
        return this;
    }

    public LocalDateFieldConstraints<T> before(Function<T, LocalDate> beforeGetter) {
        this.beforeGetter = beforeGetter;
        return this;
    }

    public LocalDateFieldConstraints<T> mustBePast(Supplier<LocalDate> currentDateSupplier) {
        this.currentDateSupplier = currentDateSupplier;
        mustBePast = true;
        return this;
    }

}
