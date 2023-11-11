package com.broniec.rest.demo.author.web.v1;

import java.util.UUID;

import lombok.Builder;

@Builder
record LocalDescriptorDTO (
        UUID id,
        String sourceSystem,
        String localIdentifier
) { }
