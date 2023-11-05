package com.broniec.rest.famework.validator;

import java.time.LocalDate;

class ConstraintViolationBuilder {

    private final String fieldLabel;

    ConstraintViolationBuilder() {
        this.fieldLabel = null;
    }

    ConstraintViolationBuilder(String fieldLabel) {
        this.fieldLabel = fieldLabel;
    }

    ConstraintViolation mandatoryField() {
        return new ConstraintViolation(fieldLabel, "Mandatory field");
    }

    ConstraintViolation minLength(int minLength) {
        return new ConstraintViolation(fieldLabel, "Min length is " + minLength);
    }

    ConstraintViolation maxLength(int maxLength) {
        return new ConstraintViolation(fieldLabel, "Max length is " + maxLength);
    }

    ConstraintViolation objectAlreadyExists() {
        return new ConstraintViolation(fieldLabel, "Object already exists");
    }

    ConstraintViolation dateMustBeBefore(LocalDate dateThatThatMustBeAfter) {
        return new ConstraintViolation(fieldLabel, "Date must be before " + dateThatThatMustBeAfter.toString());
    }

    ConstraintViolation dateMustBePast() {
        return new ConstraintViolation(fieldLabel, "Date must be past");
    }

}
