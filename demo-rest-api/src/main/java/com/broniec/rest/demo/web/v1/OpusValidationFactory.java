package com.broniec.rest.demo.web.v1;

import java.util.List;

import org.springframework.stereotype.Component;

import com.broniec.rest.demo.TimeService;
import com.broniec.rest.demo.domain.Opus;
import com.broniec.rest.demo.domain.OpusRepository;
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
        //Opus common fields
        config.addRules(identityConstraint(ArticleDTO::id, ArticleDTO.Fields.id));
        config.addRules(unicityConstraint(this::hasDuplicates));
        config.addRules(stringValidation(ArticleDTO::title, ArticleDTO.Fields.title)
                .mandatory()
                .minLength(3)
                .maxLength(255)

        );
        config.addRules(dateValidation(ArticleDTO::publicationDate, ArticleDTO.Fields.publicationDate)
                .mandatory()
                .mustBePast(timeService::currentDate)
        );
        //article-specific fields
        config.addRules(stringValidation(ArticleDTO::periodicalName, ArticleDTO.Fields.periodicalName)
                .mandatory()
                .minLength(3)
                .maxLength(255)

        );
        return new Validator<>(config);
    }

    public Validator<BookDTO> buildBookDTOValidator() {
        var config = new ValidationConfig<BookDTO>();
        //Opus common fields
        config.addRules(identityConstraint(BookDTO::id, BookDTO.Fields.id));
        config.addRules(unicityConstraint(this::hasDuplicates));
        config.addRules(stringValidation(BookDTO::title, BookDTO.Fields.title)
                .mandatory()
                .minLength(3)
                .maxLength(255)
        );
        config.addRules(dateValidation(BookDTO::publicationDate, BookDTO.Fields.publicationDate)
                .mandatory()
                .mustBePast(timeService::currentDate)
        );

        return new Validator<>(config);
    }

    public Validator<UnknownOpusDTO> buildUnknownOpusDTOValidator() {
        var config = new ValidationConfig<UnknownOpusDTO>();
        config.addRules(typeConstraint(UnknownOpusDTO::type, List.of(OpusDTO.TYPE_ARTICLE, OpusDTO.TYPE_BOOK)));

        return new Validator<>(config);
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
