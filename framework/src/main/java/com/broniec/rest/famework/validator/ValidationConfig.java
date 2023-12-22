package com.broniec.rest.famework.validator;

import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;

@Getter
public class ValidationConfig<T> {

    private final Collection<ValidationRules<T>> validationRules;
    private final Collection<ValidationRules<? super T>> superclassValidationRules;

    public ValidationConfig() {
        this.validationRules = new ArrayList<>();
        this.superclassValidationRules = new ArrayList<>();
    }

    public ValidationConfig<T> addRules(ValidationRules<T> rules) {
        validationRules.add(rules);
        return this;
    }

    public ValidationConfig<T> addSuperclassValidation(ValidationConfig<? super T> superclassValidationConfig) {
        superclassValidationRules.addAll(superclassValidationConfig.getValidationRules());
        return this;
    }



}
