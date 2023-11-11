package com.broniec.rest.demo.author.web.v1;

import java.util.UUID;

import lombok.Builder;
import lombok.experimental.FieldNameConstants;

@Builder
@FieldNameConstants
record LocalDescriptorDTO (
        UUID id,
        String sourceSystem,
        String localIdentifier
) { }
