package com.broniec.rest.demo.author.domain;

class DomainException extends RuntimeException {

    private DomainException(String message) {
        super(message);
    }

    public static DomainException authorNotFound(AuthorId authorId) {
        return new DomainException("Author not found by id %s".formatted(authorId.getValue()));
    }

}
