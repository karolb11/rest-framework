package com.broniec.rest.demo.domain;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class AuthorFacadeImpl implements AuthorFacade {

    private final AuthorRegistrationService authorRegistrationService;
    private final AuthorUpdateService authorUpdateService;
    private final OpusRegistrationService opusRegistrationService;
    private final AuthorRepository authorRepository;

    @Override
    public Author saveAuthor(Author authorToBeRegistered) {
        return authorRegistrationService.saveAuthor(authorToBeRegistered);
    }

    @Override
    public Author updateAuthor(Long authorId, Author author) {
        return authorUpdateService.updateAuthor(authorId, author);
    }

    @Override
    public Opus saveOpus(Long authorId, Opus opus) {
        return opusRegistrationService.registerOpus(authorId, opus);
    }

    @Override
    public Optional<Author> findAuthor(Long authorId) {
        return authorRepository.findById(authorId);
    }

    @Override
    public Optional<Author> findAuthor(String firstName, String lastName) {
        return authorRepository.findByFirstNameAndLastName(firstName, lastName);
    }
}
