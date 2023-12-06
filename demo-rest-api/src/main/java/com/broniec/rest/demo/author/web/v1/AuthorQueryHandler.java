package com.broniec.rest.demo.author.web.v1;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.broniec.rest.demo.author.domain.AuthorFacade;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class AuthorQueryHandler {

    private final AuthorFacade authorFacade;
    private final AuthorMapper authorMapper;

    public Optional<AuthorDTO> queryAuthorById(Long authorId) {
        return authorFacade.findAuthor(authorId)
                .map(authorMapper::toAuthorDTO);
    }
}
