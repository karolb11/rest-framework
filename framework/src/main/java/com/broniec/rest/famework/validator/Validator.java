package com.broniec.rest.famework.validator;

import java.util.Collection;
import java.util.stream.Stream;


public class Validator<T> {

    private final ValidationConfig<T> config;

    public Validator(ValidationConfig<T> config) {
        this.config = config;
    }

    public Collection<ConstraintViolation> validate(T obj, ValidationContext context) {
        return Stream.concat(
                        config.getSuperclassValidationRules().stream(),
                        config.getValidationRules().stream()
                ).flatMap(rules -> rules.execute(obj, context))
                .toList();
    }

}
