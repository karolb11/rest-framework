package com.broniec.rest.demo.author.domain;

import io.vavr.control.Either;

public interface AuthorFacade {
    Either<DomainException, Author> findAuthor(Long authorId);

    Either<DomainException, Author> findAuthor(String firstName, String lastName);

    Author saveAuthor(Author authorToBeRegistered);

    Author updateAuthor(Long authorId, Author author);
}
