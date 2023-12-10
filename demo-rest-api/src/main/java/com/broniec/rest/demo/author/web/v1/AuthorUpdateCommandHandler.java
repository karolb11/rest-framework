package com.broniec.rest.demo.author.web.v1;

import java.util.Collection;

import org.springframework.stereotype.Component;

import com.broniec.rest.demo.author.domain.Author;
import com.broniec.rest.demo.author.domain.AuthorFacade;
import com.broniec.rest.famework.validator.ConstraintViolation;
import com.broniec.rest.famework.validator.OperationType;
import com.broniec.rest.famework.validator.ValidationContext;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
class AuthorUpdateCommandHandler {

    private final AuthorMapper authorMapper;
    private final AuthorFacade authorFacade;
    private final ValidatorFactory validatorFactory;

    public Either<Collection<ConstraintViolation>, AuthorDTO> handle(Long authorId, AuthorDTO authorDTO) {
        var constraintViolations = validateAuthor(authorId, authorDTO);
        if (constraintViolations.isEmpty()) {
            var author = updateAuthor(authorId, authorDTO);
            return Either.right(authorMapper.toAuthorDTO(author));
        } else {
            return Either.left(constraintViolations);
        }
    }

    private Collection<ConstraintViolation> validateAuthor(Long authorId, AuthorDTO authorDTO) {
        var validator = validatorFactory.buildAuthorDTOValidator();
        var validationContext = ValidationContext.builder()
                .operationType(OperationType.UPDATE)
                .updatedResourceId(authorId)
                .build();
        return validator.validate(authorDTO, validationContext);
    }

    private Author updateAuthor(Long authorId, AuthorDTO authorDTO) {
        var author = authorMapper.toAuthor(authorDTO);
        author = authorFacade.updateAuthor(authorId, author);

        log.info("Author updated, id: {}", authorId);
        return author;
    }
}
