package com.broniec.rest.demo.author.domain;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthorFacade {

    private final AuthorRegistrationService authorRegistrationService;
    private final AuthorRepository authorRepository;

    public Author saveAuthor(Author authorToBeRegistered) {
        return authorRegistrationService.saveAuthor(authorToBeRegistered);
    }

    public Optional<Author> findAuthor(AuthorId authorId) {
        return authorRepository.findById(authorId);
    }

    public Optional<Author> findAuthor(String firstName, String lastName) {
        return authorRepository.findByFirstNameAndLastName(firstName, lastName);
    }
}
