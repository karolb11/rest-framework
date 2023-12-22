package com.broniec.rest.demo.web.v1;

import java.util.List;

import org.springframework.stereotype.Component;

import com.broniec.rest.demo.TimeService;
import com.broniec.rest.demo.domain.Opus;
import com.broniec.rest.demo.domain.OpusRepository;
import com.broniec.rest.famework.validator.LocalDateFieldConstraints;
import com.broniec.rest.famework.validator.StringFieldConstraints;
import com.broniec.rest.famework.validator.ValidationConfig;
import com.broniec.rest.famework.validator.ValidationContext;
import com.broniec.rest.famework.validator.Validator;
import lombok.RequiredArgsConstructor;

import static com.broniec.rest.famework.validator.ValidationRules.dateValidation;
import static com.broniec.rest.famework.validator.ValidationRules.identityConstraint;
import static com.broniec.rest.famework.validator.ValidationRules.stringValidation;
import static com.broniec.rest.famework.validator.ValidationRules.typeConstraint;
import static com.broniec.rest.famework.validator.ValidationRules.unicityConstraint;

@Component
@RequiredArgsConstructor
class OpusValidationFactory {

    private final TimeService timeService;
    private final OpusRepository opusRepository;

    public Validator<ArticleDTO> buildArticleDTOValidator() {
        var config = new ValidationConfig<ArticleDTO>();

        config.addSuperclassValidation(buildOpusValidationConfig());
        config.addRules(periodicalNameConstraints());

        return new Validator<>(config);
    }

    public Validator<BookDTO> buildBookDTOValidator() {
        var config = new ValidationConfig<BookDTO>();

        config.addSuperclassValidation(buildOpusValidationConfig());
        //for a now has only inherited fields

        return new Validator<>(config);
    }

    public Validator<UnknownOpusDTO> buildUnknownOpusDTOValidator() {
        var config = new ValidationConfig<UnknownOpusDTO>();

        config.addRules(typeConstraint(UnknownOpusDTO::type, List.of(OpusDTO.TYPE_ARTICLE, OpusDTO.TYPE_BOOK)));

        return new Validator<>(config);
    }

    private ValidationConfig<OpusDTO> buildOpusValidationConfig() {
        var config = new ValidationConfig<OpusDTO>();

        config.addRules(identityConstraint(OpusDTO::id, OpusDTO.Fields.id));
        config.addRules(unicityConstraint(this::hasDuplicates));

        config.addRules(titleConstraints());
        config.addRules(publicationDateConstraints());

        return config;
    }

    private static StringFieldConstraints<ArticleDTO> periodicalNameConstraints() {
        return stringValidation(ArticleDTO::periodicalName, ArticleDTO.Fields.periodicalName)
                .mandatory()
                .minLength(3)
                .maxLength(255);
    }

    private static StringFieldConstraints<OpusDTO> titleConstraints() {
        return stringValidation(OpusDTO::title, OpusDTO.Fields.title)
                .mandatory()
                .minLength(3)
                .maxLength(255);
    }

    private LocalDateFieldConstraints<OpusDTO> publicationDateConstraints() {
        return dateValidation(OpusDTO::publicationDate, OpusDTO.Fields.publicationDate)
                .mandatory()
                .mustBePast(timeService::currentDate);
    }

    private boolean hasDuplicates(OpusDTO opusDTO, ValidationContext context) {
        var collidingOpus = opusRepository.findByTitleAndAuthor_Id(opusDTO.title(), context.updatedAggregateResourceId());
        return switch (context.operationType()) {
            case CREATE -> !collidingOpus.isEmpty();
            case UPDATE -> collidingOpus.stream()
                    .map(Opus::getId)
                    .anyMatch(id -> !id.equals(context.updatedResourceId()));
        };
    }

}
