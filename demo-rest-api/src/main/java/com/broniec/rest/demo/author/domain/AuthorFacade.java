package com.broniec.rest.demo.author.domain;

import java.util.Optional;

public interface AuthorFacade {
    Optional<Author> findAuthor(AuthorId authorId);

    Optional<Author> findAuthor(String firstName, String lastName);

    Author saveAuthor(Author authorToBeRegistered);
}
