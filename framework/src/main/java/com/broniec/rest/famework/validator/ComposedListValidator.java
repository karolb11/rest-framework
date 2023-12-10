package com.broniec.rest.famework.validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class ComposedListValidator<O, F> implements ValidationRules<O> {

    private final Function<O, List<F>> valueGetter;
    private final String fieldLabel;
    private final Validator<F> validator;
    private final Collection<Constraint<O, List<F>>> constraints;

    ComposedListValidator(Function<O, List<F>> valueGetter, String fieldLabel, ValidationConfig<F> validationConfig) {
        this.valueGetter = valueGetter;
        this.fieldLabel = fieldLabel;
        this.validator = new Validator<>(validationConfig);
        this.constraints = new ArrayList<>();
        constraints.add(new ComposedListConstraint<>(fieldLabel, validator));
    }


    @Override
    public Stream<ConstraintViolation> execute(O obj, ValidationContext context) {
        var value = valueGetter.apply(obj);
        return constraints.stream().flatMap(constraint -> constraint.check(obj, context, value, fieldLabel));
    }

    public ComposedListValidator<O, F> mandatory() {
        constraints.add(new MandatoryObjectConstraint<>(fieldLabel));
        return this;
    }

}
