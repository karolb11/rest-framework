package com.broniec.rest.demo.author.web.v1;

import java.util.List;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.broniec.rest.demo.TimeService;
import com.broniec.rest.demo.author.domain.AuthorFacade;
import com.broniec.rest.famework.validator.ComposedValidator;
import com.broniec.rest.famework.validator.ValidationConfig;
import lombok.RequiredArgsConstructor;

import static com.broniec.rest.famework.validator.ValidationRules.composedListValidator;
import static com.broniec.rest.famework.validator.ValidationRules.composedValidator;
import static com.broniec.rest.famework.validator.ValidationRules.dateValidation;
import static com.broniec.rest.famework.validator.ValidationRules.listValidator;
import static com.broniec.rest.famework.validator.ValidationRules.stringValidation;
import static com.broniec.rest.famework.validator.ValidationRules.unicityConstraint;

@Component
@RequiredArgsConstructor
class ValidationConfigFactory {

    private final AuthorFacade authorFacade;
    private final TimeService timeService;

    ValidationConfig<AuthorDTO> buildAuthorDTOValidationConfig() {
        var descriptorValidator = buildLocalDescriptorDTOValidationConfig();
        var config = new ValidationConfig<AuthorDTO>();
        config.addRules(unicityConstraint(this::hasDuplicates));
        config.addRules(stringValidation(AuthorDTO::firstName, AuthorDTO.Fields.firstName)
                .mandatory()
                .minLength(3)
                .maxLength(100)
        );
        config.addRules(stringValidation(AuthorDTO::lastName, AuthorDTO.Fields.lastName)
                .mandatory()
                .minLength(3)
                .maxLength(100)
        );
        config.addRules(dateValidation(AuthorDTO::dateOfBirth, AuthorDTO.Fields.dateOfBirth)
                .mandatory()
                .mustBePast(timeService::currentDate)
                .before(AuthorDTO::dateOfDeath)
        );
        config.addRules(dateValidation(AuthorDTO::dateOfDeath, AuthorDTO.Fields.dateOfDeath)
                .mustBePast(timeService::currentDate)
        );
        config.addRules(listValidator(AuthorDTO::localDescriptor, AuthorDTO.Fields.localDescriptor)
                .mandatory()
        );

        config.addRules(composedListValidator(AuthorDTO::localDescriptor, AuthorDTO.Fields.localDescriptor, descriptorValidator));
        return config;
    }

    private boolean hasDuplicates(AuthorDTO AuthorDTO) {
        return authorFacade.findAuthor(
                AuthorDTO.firstName(),
                AuthorDTO.lastName()
        ).isPresent();
    }

    ValidationConfig<LocalDescriptorDTO> buildLocalDescriptorDTOValidationConfig() {
        var config = new ValidationConfig<LocalDescriptorDTO>();
        config.addRules(stringValidation(LocalDescriptorDTO::localIdentifier, LocalDescriptorDTO.Fields.localIdentifier)
                .mandatory()
                .maxLength(30)
        );
        config.addRules(stringValidation(LocalDescriptorDTO::sourceSystem, LocalDescriptorDTO.Fields.sourceSystem)
                .mandatory()
                .maxLength(100)
        );
        return config;
    }

}
