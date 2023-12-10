package com.broniec.rest.demo.author.web.v1;

import java.util.Collection;

import org.springframework.stereotype.Component;

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
class AuthorCommandHandler {

    private final AuthorMapper authorMapper;
    private final AuthorFacade authorFacade;
    private final ValidatorFactory validatorFactory;

    public Either<Collection<ConstraintViolation>, AuthorDTO> registerAuthor(AuthorDTO authorDTO) {
        var validator = validatorFactory.buildAuthorDTOValidator();
        var validationContext = ValidationContext.builder()
                .operationType(OperationType.CREATE)
                .build();
        var constraintViolations = validator.validate(authorDTO, validationContext);
        if (constraintViolations.isEmpty()) {
            var author = authorMapper.toAuthor(authorDTO);
            author = authorFacade.saveAuthor(author);

            log.info("Author registered, id: {}", author.getId());
            return Either.right(authorMapper.toAuthorDTO(author));
        } else {
            return Either.left(constraintViolations);
        }
    }

    public Either<Collection<ConstraintViolation>, AuthorDTO> updateAuthor(Long authorId, AuthorDTO authorDTO) {
        var validator = validatorFactory.buildAuthorDTOValidator();
        var validationContext = ValidationContext.builder()
                .operationType(OperationType.UPDATE)
                .updatedResourceId(authorId)
                .build();
        var constraintViolations = validator.validate(authorDTO, validationContext);
        if (constraintViolations.isEmpty()) {
            var author = authorMapper.toAuthor(authorDTO);
            author = authorFacade.updateAuthor(authorId, author);

            log.info("Author updated, id: {}", authorId);
            return Either.right(authorMapper.toAuthorDTO(author));
        } else {
            return Either.left(constraintViolations);
        }
    }
}
