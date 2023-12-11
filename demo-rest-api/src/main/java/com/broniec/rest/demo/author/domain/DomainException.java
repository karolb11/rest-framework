package com.broniec.rest.demo.author.domain;


public class DomainException extends RuntimeException {

    /*
    Private constructor to forbid instantiation from outside
    Default access of building methods, because only domain must be able to throw DomainsExceptions
     */

    private DomainException(String message) {
        super(message);
    }

    static DomainException authorNotFound(Long authorId) {
        return new DomainException("Author not found by id %s".formatted(authorId));
    }

    static DomainException authorNotFound(String firstName, String lastName) {
        return new DomainException("Author not found by first name (%s) and last name (%s)".formatted(firstName, lastName));
    }

}
