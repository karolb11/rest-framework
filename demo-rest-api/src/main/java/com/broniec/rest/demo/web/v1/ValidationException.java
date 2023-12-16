package com.broniec.rest.demo.web.v1;

import java.util.Collection;

import com.broniec.rest.famework.validator.ConstraintViolation;
import lombok.Getter;

@Getter
class ValidationException extends Exception {

    private final Collection<ConstraintViolation> constraintViolations;

    public ValidationException(Collection<ConstraintViolation> constraintViolations) {
        this.constraintViolations = constraintViolations;
    }
}
