package com.broniec.rest.famework.validator;

import lombok.Builder;

@Builder
public record ValidationContext(OperationType operationType,
                                Long updatedAggregateResourceId,
                                Long updatedResourceId) {

    public boolean isCreate() {
        return operationType == OperationType.CREATE;
    }

    public boolean isUpdate() {
        return operationType == OperationType.UPDATE;
    }


}
