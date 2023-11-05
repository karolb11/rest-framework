package com.broniec.rest.demo.author.web.v1;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.broniec.rest.demo.author.domain.AuthorFacade;
import com.broniec.rest.demo.author.domain.AuthorId;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class AuthorQueryHandler {

    private final AuthorFacade authorFacade;
    private final AuthorMapper authorMapper;

    public Optional<AuthorDTO> queryAuthorById(UUID authorId) {
        return authorFacade.findAuthor(new AuthorId(authorId))
                .map(authorMapper::toAuthorDTO);
    }
}
