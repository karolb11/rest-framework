package com.broniec.rest.famework;


import java.util.Collection;
import java.util.Collections;
import java.util.function.Supplier;

public class Objects {

    private Objects() { };

    public static  <T> Collection<T> optionalCollection(Supplier<Collection<T>> supplier) {
        try {
            return supplier.get();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

}
