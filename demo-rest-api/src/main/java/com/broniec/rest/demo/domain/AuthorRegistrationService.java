package com.broniec.rest.demo.domain;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
class AuthorRegistrationService {

    private final AuthorRepository authorRepository;

    public Author saveAuthor(Author authorToBeRegistered) {
        return authorRepository.save(authorToBeRegistered);
    }
}
