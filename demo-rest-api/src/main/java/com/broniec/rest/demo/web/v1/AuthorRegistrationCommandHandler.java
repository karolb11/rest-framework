package com.broniec.rest.demo.web.v1;

import java.util.Collection;

import org.springframework.stereotype.Component;

import com.broniec.rest.demo.domain.Author;
import com.broniec.rest.demo.domain.AuthorFacade;
import com.broniec.rest.famework.validator.ConstraintViolation;
import com.broniec.rest.famework.validator.ValidationContext;
import com.broniec.rest.famework.validator.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
class AuthorRegistrationCommandHandler {

    private final AuthorMapper authorMapper;
    private final AuthorFacade authorFacade;
    private final AuthorValidatorFactory authorValidatorFactory;

    public AuthorDTO handle(AuthorDTO authorDTO) throws ValidationException {
        var constraintViolations = validateAuthor(authorDTO);
        if (constraintViolations.isEmpty()) {
            var author = saveAuthor(authorDTO);
            return authorMapper.toAuthorDTO(author);
        } else {
            throw new ValidationException(constraintViolations);
        }
    }

    private Collection<ConstraintViolation> validateAuthor(AuthorDTO authorDTO) {
        var validator = authorValidatorFactory.buildAuthorDTOValidator();
        var validationContext = ValidationContext.create();
        return validator.validate(authorDTO, validationContext);
    }

    private Author saveAuthor(AuthorDTO authorDTO) {
        var author = authorMapper.toAuthor(authorDTO);
        author = authorFacade.saveAuthor(author);

        log.info("Author registered, id: {}", author.getId());
        return author;
    }

}
