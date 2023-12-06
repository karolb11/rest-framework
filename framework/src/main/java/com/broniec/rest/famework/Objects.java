package com.broniec.rest.famework;


import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Supplier;

public class Objects {

    private Objects() { }

    public static  <T> Collection<T> optionalCollection(Supplier<Collection<T>> supplier) {
        try {
            return Optional.ofNullable(supplier.get()).orElse(Collections.emptyList());
        } catch (Exception e) {

            return Collections.emptyList();
        }
    }

}
