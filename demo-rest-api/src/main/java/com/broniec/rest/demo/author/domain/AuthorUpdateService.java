package com.broniec.rest.demo.author.domain;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
class AuthorUpdateService {

    private final AuthorRepository authorRepository;

    @Transactional
    public Author updateAuthor(Long authorId, Author newAuthor) {
        var authorToBeUpdated = authorRepository.findById(authorId)
                .orElseThrow(() -> DomainException.authorNotFound(authorId));
        authorToBeUpdated.update(newAuthor);
        return authorToBeUpdated;
    }
}
