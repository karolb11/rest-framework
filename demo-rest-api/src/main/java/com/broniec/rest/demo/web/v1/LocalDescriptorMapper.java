package com.broniec.rest.demo.web.v1;

import org.springframework.stereotype.Component;

import com.broniec.rest.demo.domain.LocalDescriptor;

@Component
class LocalDescriptorMapper {

    public LocalDescriptor toLocalDescriptor(LocalDescriptorDTO localDescriptorDTO) {
        return LocalDescriptor.builder()
                .id(localDescriptorDTO.id())
                .localIdentifier(localDescriptorDTO.localIdentifier())
                .sourceSystem(localDescriptorDTO.sourceSystem())
                .build();
    }

    public LocalDescriptorDTO toLocalDescriptorDTO(LocalDescriptor localDescriptor) {
        return LocalDescriptorDTO.builder()
                .id(localDescriptor.getId())
                .localIdentifier(localDescriptor.getLocalIdentifier())
                .sourceSystem(localDescriptor.getSourceSystem())
                .build();
    }

}
