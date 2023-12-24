package com.broniec.rest.demo.web.v1.opus;

import java.util.Collection;

import org.springframework.stereotype.Component;

import com.broniec.rest.demo.domain.AuthorFacade;
import com.broniec.rest.demo.domain.Opus;
import com.broniec.rest.famework.validator.ConstraintViolation;
import com.broniec.rest.famework.validator.ValidationContext;
import com.broniec.rest.famework.validator.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class OpusRegistrationCommandHandler {

    private final AuthorFacade authorFacade;
    private final OpusMapper opusMapper;
    private final OpusValidationFactory opusValidationFactory;

    public OpusDTO handle(Long authorId, OpusDTO opusDTO) throws ValidationException {
        var constraintViolations = validateOpus(authorId, opusDTO);
        if (constraintViolations.isEmpty()) {
            var opus = saveOpus(authorId, opusDTO);
            return opusMapper.toDTO(opus);
        } else {
            throw new ValidationException(constraintViolations);
        }
    }

    private Collection<ConstraintViolation> validateOpus(Long authorId, OpusDTO opusDTO) {
        var validationContext = ValidationContext.create()
                .withUpdatedAggregateResourceId(authorId);
        return switch (opusDTO) {
            case ArticleDTO article -> opusValidationFactory.buildArticleDTOValidator().validate(article, validationContext);
            case BookDTO book -> opusValidationFactory.buildBookDTOValidator().validate(book, validationContext);
            case UnknownOpusDTO unknown -> opusValidationFactory.buildUnknownOpusDTOValidator().validate(unknown, validationContext);
        };

    }

    private Opus saveOpus(Long authorId, OpusDTO opusDTO) {
        var opus = opusMapper.toEntity(opusDTO);
        opus = authorFacade.saveOpus(authorId, opus);

        log.info("Opus registered, id: {}", opus.getId());
        return opus;
    }
}
