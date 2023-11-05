package com.broniec.rest.demo.author.web.v1;

import java.util.Collection;

import org.springframework.stereotype.Component;

import com.broniec.rest.demo.author.domain.AuthorFacade;
import com.broniec.rest.famework.validator.ConstraintViolation;
import com.broniec.rest.famework.validator.Validator;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class AuthorCommandHandler {

    private final AuthorMapper authorMapper;
    private final AuthorFacade authorFacade;
    private final ValidationConfigFactory validationConfigFactory;

    public Either<Collection<ConstraintViolation>, AuthorDTO> registerAuthor(AuthorDTO authorDTO) {
        var validator = new Validator<>(validationConfigFactory.buildAuthorDTOValidationConfig());
        var constraintViolations = validator.validate(authorDTO);
        if (constraintViolations.isEmpty()) {
            var author = authorMapper.toAuthor(authorDTO);
            author = authorFacade.saveAuthor(author);
            return Either.right(authorMapper.toAuthorDTO(author));
        } else {
            return Either.left(constraintViolations);
        }
    }

}
