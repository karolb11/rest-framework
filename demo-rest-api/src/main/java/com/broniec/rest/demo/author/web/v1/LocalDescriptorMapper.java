package com.broniec.rest.demo.author.web.v1;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.broniec.rest.demo.author.domain.LocalDescriptor;
import com.broniec.rest.demo.author.domain.LocalDescriptorId;

@Component
class LocalDescriptorMapper {

    public LocalDescriptor toLocalDescriptor(LocalDescriptorDTO localDescriptorDTO) {
        var id = Optional.ofNullable(localDescriptorDTO.id())
                .map(LocalDescriptorId::new)
                .orElse(null);
        return LocalDescriptor.builder()
                .id(id)
                .localIdentifier(localDescriptorDTO.localIdentifier())
                .sourceSystem(localDescriptorDTO.sourceSystem())
                .build();
    }

    public LocalDescriptorDTO toLocalDescriptorDTO(LocalDescriptor localDescriptor) {
        return LocalDescriptorDTO.builder()
                .id(localDescriptor.getId().getValue())
                .localIdentifier(localDescriptor.getLocalIdentifier())
                .sourceSystem(localDescriptor.getSourceSystem())
                .build();
    }

}
