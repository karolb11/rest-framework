package com.broniec.rest.famework.validator;

import java.util.ArrayList;
import java.util.Collection;

public class ValidationConfig<T> {

    private final Collection<ValidationRules<T>> validationRules;

    Collection<ValidationRules<T>> getValidationRules() {
        return validationRules;
    }

    public ValidationConfig() {
        this.validationRules = new ArrayList<>();
    }

    public ValidationConfig<T> addRules(ValidationRules<T> rules) {
        validationRules.add(rules);
        return this;
    }



}
