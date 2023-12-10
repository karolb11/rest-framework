package com.broniec.rest.famework.validator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.Objects.isNull;

public class LocalDateFieldConstraints<O> implements ValidationRules<O> {

    private final Function<O, LocalDate> valueGetter;
    private final String fieldLabel;
    private final Collection<Constraint<O, LocalDate>> constraints;

    public LocalDateFieldConstraints(Function<O, LocalDate> valueGetter, String fieldLabel) {
        this.valueGetter = valueGetter;
        this.fieldLabel = fieldLabel;
        this.constraints = new ArrayList<>();
    }

    @Override
    public Stream<ConstraintViolation> execute(O obj, ValidationContext context) {
        var value = valueGetter.apply(obj);
        return constraints.stream().flatMap(constraint -> constraint.check(obj, context, value, fieldLabel));
    }

    public LocalDateFieldConstraints<O> mandatory() {
        constraints.add(new MandatoryObjectConstraint<>(fieldLabel));
        return this;
    }

    public LocalDateFieldConstraints<O> before(Function<O, LocalDate> beforeGetter) {
        if (isNull(beforeGetter)) {
            return this;
        }
        constraints.add(new DateBeforeConstraint<>(fieldLabel, beforeGetter));
        return this;
    }

    public LocalDateFieldConstraints<O> mustBePast(Supplier<LocalDate> currentDateSupplier) {
        constraints.add(new PastDateConstraint<>(fieldLabel, currentDateSupplier));
        return this;
    }

}
