package com.broniec.rest.famework.validator;

@FunctionalInterface
public interface ValidationPredicate<T> {

    boolean test(T t, ValidationContext context);

}
