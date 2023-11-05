package com.broniec.rest.demo.author.web.v1;

import org.springframework.stereotype.Component;

import com.broniec.rest.demo.TimeService;
import com.broniec.rest.demo.author.domain.AuthorFacade;
import com.broniec.rest.famework.validator.ValidationConfig;
import com.broniec.rest.famework.validator.ValidationRules;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class ValidationConfigFactory {

    private final AuthorFacade authorFacade;
    private final TimeService timeService;

    ValidationConfig<AuthorDTO> buildAuthorDTOValidationConfig() {
        var config = new ValidationConfig<AuthorDTO>();
        config.addRules(ValidationRules.unicityConstraint(this::hasDuplicates));
        config.addRules(ValidationRules.stringValidation(AuthorDTO::firstName, AuthorDTO.Fields.firstName)
                .mandatory()
                .minLength(3)
                .maxLength(100)
        );
        config.addRules(ValidationRules.stringValidation(AuthorDTO::lastName, AuthorDTO.Fields.lastName)
                .mandatory()
                .minLength(3)
                .maxLength(100)
        );
        config.addRules(ValidationRules.dateValidation(AuthorDTO::dateOfBirth, AuthorDTO.Fields.dateOfBirth)
                .mandatory()
                .mustBePast(timeService::currentDate)
                .before(AuthorDTO::dateOfDeath)
        );
        config.addRules(ValidationRules.dateValidation(AuthorDTO::dateOfDeath, AuthorDTO.Fields.dateOfDeath)
                .mustBePast(timeService::currentDate)
        );
        return config;
    }

    private boolean hasDuplicates(AuthorDTO AuthorDTO) {
        return authorFacade.findAuthor(
                AuthorDTO.firstName(),
                AuthorDTO.lastName()
        ).isPresent();
    }

}
