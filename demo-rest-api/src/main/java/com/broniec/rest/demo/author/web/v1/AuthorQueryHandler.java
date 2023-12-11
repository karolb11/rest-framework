package com.broniec.rest.demo.author.web.v1;

import org.springframework.stereotype.Component;

import com.broniec.rest.demo.author.domain.AuthorFacade;
import com.broniec.rest.demo.author.domain.DomainException;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class AuthorQueryHandler {

    private final AuthorFacade authorFacade;
    private final AuthorMapper authorMapper;

    public Either<DomainException, AuthorDTO> queryAuthorById(Long authorId) {
        return authorFacade.findAuthor(authorId)
                .map(authorMapper::toAuthorDTO);
    }

}
