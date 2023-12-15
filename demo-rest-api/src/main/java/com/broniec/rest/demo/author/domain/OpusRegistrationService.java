package com.broniec.rest.demo.author.domain;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class OpusRegistrationService {

    private final AuthorRepository authorRepository;

    @Transactional
    public Opus registerOpus(Long authorId, Opus opus) {
        var author = authorRepository.findById(authorId).orElseThrow(() -> DomainException.authorNotFound(authorId));
        author.addOpus(opus);
        return opus;
    }
}
