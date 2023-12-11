package com.broniec.rest.demo.author.domain;

import org.springframework.stereotype.Component;

import io.vavr.control.Either;
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
    public Author updateAuthor(Long authorId, Author author) {
        return authorUpdateService.updateAuthor(authorId, author);
    }

    @Override
    public Either<DomainException, Author> findAuthor(Long authorId) {
        return authorRepository.findById(authorId)
                .<Either<DomainException, Author>>map(Either::right)
                .orElseGet(() -> Either.left(DomainException.authorNotFound(authorId)));
    }

    @Override
    public Either<DomainException, Author> findAuthor(String firstName, String lastName) {
        return authorRepository.findByFirstNameAndLastName(firstName, lastName)
                .<Either<DomainException, Author>>map(Either::right)
                .orElseGet(() -> Either.left(DomainException.authorNotFound(firstName, lastName)));
    }
}
