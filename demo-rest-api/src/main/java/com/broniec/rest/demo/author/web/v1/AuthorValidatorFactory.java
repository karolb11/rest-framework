package com.broniec.rest.demo.author.web.v1;

import org.springframework.stereotype.Component;

import com.broniec.rest.demo.TimeService;
import com.broniec.rest.demo.author.domain.Author;
import com.broniec.rest.demo.author.domain.AuthorFacade;
import com.broniec.rest.famework.validator.ValidationConfig;
import com.broniec.rest.famework.validator.ValidationContext;
import com.broniec.rest.famework.validator.Validator;
import lombok.RequiredArgsConstructor;

import static com.broniec.rest.famework.validator.ValidationRules.composedListValidator;
import static com.broniec.rest.famework.validator.ValidationRules.dateValidation;
import static com.broniec.rest.famework.validator.ValidationRules.identityConstraint;
import static com.broniec.rest.famework.validator.ValidationRules.stringValidation;
import static com.broniec.rest.famework.validator.ValidationRules.unicityConstraint;

@Component
@RequiredArgsConstructor
class AuthorValidatorFactory {

    private final AuthorFacade authorFacade;
    private final TimeService timeService;

    public Validator<AuthorDTO> buildAuthorDTOValidator() {
        var descriptorValidator = buildLocalDescriptorDTOValidationConfig();
        var config = new ValidationConfig<AuthorDTO>();
        config.addRules(identityConstraint(AuthorDTO::id, AuthorDTO.Fields.id));
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

        config.addRules(composedListValidator(AuthorDTO::localDescriptor, AuthorDTO.Fields.localDescriptor, descriptorValidator));
        return new Validator<>(config);
    }

    private boolean hasDuplicates(AuthorDTO authorDTO, ValidationContext context) {
        var collidingAuthor = authorFacade.findAuthor(authorDTO.firstName(), authorDTO.lastName());
        return switch (context.operationType()) {
            case CREATE -> collidingAuthor.isPresent();
            case UPDATE -> collidingAuthor
                    .map(Author::getId)
                    .filter(id -> !id.equals(context.updatedAggregateResourceId()))
                    .isPresent();
        };
    }

    private ValidationConfig<LocalDescriptorDTO> buildLocalDescriptorDTOValidationConfig() {
        var config = new ValidationConfig<LocalDescriptorDTO>();
        config.addRules(identityConstraint(LocalDescriptorDTO::id, LocalDescriptorDTO.Fields.id));
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
