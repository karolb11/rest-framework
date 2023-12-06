package com.broniec.rest.demo.author.domain;

import java.util.UUID;

import com.broniec.rest.famework.ValueObject;


public class AuthorId extends ValueObject<UUID> {
    public AuthorId(UUID value) {
        super(value);
    }
}
