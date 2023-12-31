package com.broniec.rest.demo.domain;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
class AuthorUpdateService {

    private final AuthorRepository authorRepository;

    @Transactional
    public Author updateAuthor(Long authorId, Author updateReference) {
        var authorToBeUpdated = authorRepository.findById(authorId)
                .orElseThrow(() -> DomainException.authorNotFound(authorId));
        authorToBeUpdated.update(updateReference);
        return authorToBeUpdated;
    }
}
