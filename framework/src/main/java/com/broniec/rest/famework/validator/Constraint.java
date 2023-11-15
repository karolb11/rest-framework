package com.broniec.rest.famework.validator;

import java.util.stream.Stream;

interface Constraint<O, F> {

    Stream<ConstraintViolation> check(O validatedObj, F value, String fieldLabel);

}
