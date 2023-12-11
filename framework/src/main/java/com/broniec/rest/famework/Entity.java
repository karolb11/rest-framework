package com.broniec.rest.famework;

public interface Entity<T, ID> {

    ID getId();

    void update(T reference);

    T copy();

}
