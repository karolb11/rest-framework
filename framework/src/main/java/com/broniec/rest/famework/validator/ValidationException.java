package com.broniec.rest.famework.validator;

import java.util.Collection;

import lombok.Getter;

@Getter
public class ValidationException extends Exception {

    private final Collection<ConstraintViolation> constraintViolations;

    public ValidationException(Collection<ConstraintViolation> constraintViolations) {
        this.constraintViolations = constraintViolations;
    }
}
