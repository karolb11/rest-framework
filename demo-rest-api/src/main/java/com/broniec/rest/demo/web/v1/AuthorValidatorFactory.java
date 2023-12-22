package com.broniec.rest.demo.web.v1;

import org.springframework.stereotype.Component;

import com.broniec.rest.demo.TimeService;
import com.broniec.rest.demo.domain.Author;
import com.broniec.rest.demo.domain.AuthorFacade;
import com.broniec.rest.famework.validator.LocalDateFieldConstraints;
import com.broniec.rest.famework.validator.StringFieldConstraints;
import com.broniec.rest.famework.validator.ValidationConfig;
import com.broniec.rest.famework.validator.ValidationContext;
import com.broniec.rest.famework.validator.ValidationRules;
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
    private final LocalDescriptorValidatorFactory localDescriptorValidatorFactory;
    private final TimeService timeService;

    public Validator<AuthorDTO> buildAuthorDTOValidator() {
        var config = new ValidationConfig<AuthorDTO>();

        config.addRules(identityConstraint(AuthorDTO::id, AuthorDTO.Fields.id));
        config.addRules(unicityConstraint(this::hasDuplicates));

        config.addRules(firstNameConstrants());
        config.addRules(lastNameConstraints());
        config.addRules(dateOfBirthConstraints());
        config.addRules(dateOfDeathConstraints());
        config.addRules(localDescriptorConstraints());

        return new Validator<>(config);
    }

    private static StringFieldConstraints<AuthorDTO> firstNameConstrants() {
        return stringValidation(AuthorDTO::firstName, AuthorDTO.Fields.firstName)
                .mandatory()
                .minLength(3)
                .maxLength(100);
    }

    private static StringFieldConstraints<AuthorDTO> lastNameConstraints() {
        return stringValidation(AuthorDTO::lastName, AuthorDTO.Fields.lastName)
                .mandatory()
                .minLength(3)
                .maxLength(100);
    }

    private LocalDateFieldConstraints<AuthorDTO> dateOfBirthConstraints() {
        return dateValidation(AuthorDTO::dateOfBirth, AuthorDTO.Fields.dateOfBirth)
                .mandatory()
                .mustBePast(timeService::currentDate)
                .before(AuthorDTO::dateOfDeath);
    }

    private LocalDateFieldConstraints<AuthorDTO> dateOfDeathConstraints() {
        return dateValidation(AuthorDTO::dateOfDeath, AuthorDTO.Fields.dateOfDeath)
                .mustBePast(timeService::currentDate);
    }

    private ValidationRules<AuthorDTO> localDescriptorConstraints() {
        var descriptorValidator = localDescriptorValidatorFactory.buildLocalDescriptorDTOValidationConfig();
        return composedListValidator(AuthorDTO::localDescriptor, AuthorDTO.Fields.localDescriptor, descriptorValidator);
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

}
