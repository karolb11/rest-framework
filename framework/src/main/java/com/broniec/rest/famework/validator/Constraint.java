package com.broniec.rest.famework.validator;

import java.util.stream.Stream;

interface Constraint<T> {

    Stream<ConstraintViolation> check(T value, String fieldLabel);

}
