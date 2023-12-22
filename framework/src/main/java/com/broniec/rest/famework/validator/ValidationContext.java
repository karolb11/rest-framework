package com.broniec.rest.famework.validator;

import lombok.With;

@With
public record ValidationContext(OperationType operationType,
                                Long updatedAggregateResourceId,
                                Long updatedResourceId) {

    private ValidationContext(OperationType operationType) {
        this(operationType, null, null);
    }

    public static ValidationContext create() {
        return new ValidationContext(OperationType.CREATE);
    }

    public static ValidationContext update() {
        return new ValidationContext(OperationType.UPDATE);
    }

    public boolean isCreate() {
        return operationType == OperationType.CREATE;
    }

    public boolean isUpdate() {
        return operationType == OperationType.UPDATE;
    }


}
