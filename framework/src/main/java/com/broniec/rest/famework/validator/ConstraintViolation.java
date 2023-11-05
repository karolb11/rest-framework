package com.broniec.rest.famework.validator;

public record ConstraintViolation(
        String field,
        String message
) {

}
