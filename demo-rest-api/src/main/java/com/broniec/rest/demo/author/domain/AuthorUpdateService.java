package com.broniec.rest.demo.author.domain;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
class AuthorUpdateService {

    private final AuthorRepository authorRepository;

    public Author updateAuthor(AuthorId authorId, Author newAuthor) {
        var authorToBeUpdated = authorRepository.findById(authorId)
                .orElseThrow(() -> DomainException.authorNotFound(authorId));
        authorToBeUpdated.update(newAuthor);
        return null;
    }
}
