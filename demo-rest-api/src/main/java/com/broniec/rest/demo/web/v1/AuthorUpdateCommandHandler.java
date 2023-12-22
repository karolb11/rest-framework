package com.broniec.rest.demo.web.v1;

import java.util.Collection;

import org.springframework.stereotype.Component;

import com.broniec.rest.demo.domain.Author;
import com.broniec.rest.demo.domain.AuthorFacade;
import com.broniec.rest.famework.validator.ConstraintViolation;
import com.broniec.rest.famework.validator.ValidationContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
class AuthorUpdateCommandHandler {

    private final AuthorMapper authorMapper;
    private final AuthorFacade authorFacade;
    private final AuthorValidatorFactory authorValidatorFactory;

    public AuthorDTO handle(Long authorId, AuthorDTO authorDTO) throws ValidationException {
        var constraintViolations = validateAuthor(authorId, authorDTO);
        if (constraintViolations.isEmpty()) {
            var author = updateAuthor(authorId, authorDTO);
            return authorMapper.toAuthorDTO(author);
        } else {
            throw new ValidationException(constraintViolations);
        }
    }

    private Collection<ConstraintViolation> validateAuthor(Long authorId, AuthorDTO authorDTO) {
        var validator = authorValidatorFactory.buildAuthorDTOValidator();
        var validationContext = ValidationContext.update()
                .withUpdatedAggregateResourceId(authorId);
        return validator.validate(authorDTO, validationContext);
    }

    private Author updateAuthor(Long authorId, AuthorDTO authorDTO) {
        var author = authorMapper.toAuthor(authorDTO);
        author = authorFacade.updateAuthor(authorId, author);

        log.info("Author updated, id: {}", authorId);
        return author;
    }
}
