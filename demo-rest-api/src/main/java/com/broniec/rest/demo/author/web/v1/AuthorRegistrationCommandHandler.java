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
class AuthorRegistrationCommandHandler {

    private final AuthorMapper authorMapper;
    private final AuthorFacade authorFacade;
    private final ValidatorFactory validatorFactory;

    public Either<Collection<ConstraintViolation>, AuthorDTO> registerAuthor(AuthorDTO authorDTO) {
        var constraintViolations = validateAuthor(authorDTO);
        if (constraintViolations.isEmpty()) {
            var author = saveAuthor(authorDTO);
            return Either.right(authorMapper.toAuthorDTO(author));
        } else {
            return Either.left(constraintViolations);
        }
    }

    private Collection<ConstraintViolation> validateAuthor(AuthorDTO authorDTO) {
        var validator = validatorFactory.buildAuthorDTOValidator();
        var validationContext = ValidationContext.builder()
                .operationType(OperationType.CREATE)
                .build();
        return validator.validate(authorDTO, validationContext);
    }

    private Author saveAuthor(AuthorDTO authorDTO) {
        var author = authorMapper.toAuthor(authorDTO);
        author = authorFacade.saveAuthor(author);

        log.info("Author registered, id: {}", author.getId());
        return author;
    }

}
