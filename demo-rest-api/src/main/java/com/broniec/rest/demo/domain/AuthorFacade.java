package com.broniec.rest.demo.domain;

import java.util.Optional;

public interface AuthorFacade {
    Optional<Author> findAuthor(Long authorId);

    Optional<Author> findAuthor(String firstName, String lastName);

    Author saveAuthor(Author authorToBeRegistered);

    Author updateAuthor(Long authorId, Author author);

    Opus saveOpus(Long authorId, Opus opus);

}
