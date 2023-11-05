package com.broniec.rest.demo.author.domain;

import java.util.Optional;

interface AuthorRepository {

    Author save(Author author);

    Optional<Author> findById(AuthorId authorId);

    Optional<Author> findByFirstNameAndLastName(String firstName, String lastName);

}
