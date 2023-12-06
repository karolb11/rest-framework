package com.broniec.rest.demo.author.domain;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class AuthorFacadeImpl implements AuthorFacade {

    private final AuthorRegistrationService authorRegistrationService;
    private final AuthorUpdateService authorUpdateService;
    private final AuthorRepository authorRepository;

    @Override
    public Author saveAuthor(Author authorToBeRegistered) {
        return authorRegistrationService.saveAuthor(authorToBeRegistered);
    }

    @Override
    public Author updateAuthor(AuthorId authorId, Author author) {
        return authorUpdateService.updateAuthor(authorId, author);
    }

    @Override
    public Optional<Author> findAuthor(AuthorId authorId) {
        return authorRepository.findById(authorId);
    }

    @Override
    public Optional<Author> findAuthor(String firstName, String lastName) {
        return authorRepository.findByFirstNameAndLastName(firstName, lastName);
    }
}
