package com.broniec.rest.famework.validator;

import java.util.Collection;


public class Validator<T> {

    private final ValidationConfig<T> config;

    public Validator(ValidationConfig<T> config) {
        this.config = config;
    }

    public Collection<ConstraintViolation> validate(T obj) {
        return config.getValidationRules().stream()
                .flatMap(rules -> rules.execute(obj))
                .toList();
    }

}
