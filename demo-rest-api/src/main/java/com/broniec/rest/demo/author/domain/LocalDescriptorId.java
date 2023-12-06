package com.broniec.rest.demo.author.domain;

import java.util.UUID;

import com.broniec.rest.famework.ValueObject;

public class LocalDescriptorId extends ValueObject<UUID> {

    public LocalDescriptorId(UUID value) {
        super(value);
    }

}
