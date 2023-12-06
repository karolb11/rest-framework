package com.broniec.rest.famework;

import java.util.UUID;

public interface Entity<T> {

    ValueObject<UUID> getId();

    void update(T reference);

}
