package com.broniec.rest.demo.web.v1;

import org.springframework.stereotype.Component;

import com.broniec.rest.famework.validator.StringFieldConstraints;
import com.broniec.rest.famework.validator.ValidationConfig;

import static com.broniec.rest.famework.validator.ValidationRules.identityConstraint;
import static com.broniec.rest.famework.validator.ValidationRules.stringValidation;

@Component
class LocalDescriptorValidatorFactory {

    public ValidationConfig<LocalDescriptorDTO> buildLocalDescriptorDTOValidationConfig() {
        var config = new ValidationConfig<LocalDescriptorDTO>();
        config.addRules(identityConstraint(LocalDescriptorDTO::id, LocalDescriptorDTO.Fields.id));

        config.addRules(localIdentifierConstraints());
        config.addRules(sourceSystemConstraints());

        return config;
    }

    private static StringFieldConstraints<LocalDescriptorDTO> localIdentifierConstraints() {
        return stringValidation(LocalDescriptorDTO::localIdentifier, LocalDescriptorDTO.Fields.localIdentifier)
                .mandatory()
                .maxLength(30);
    }

    private static StringFieldConstraints<LocalDescriptorDTO> sourceSystemConstraints() {
        return stringValidation(LocalDescriptorDTO::sourceSystem, LocalDescriptorDTO.Fields.sourceSystem)
                .mandatory()
                .maxLength(100);
    }

}
