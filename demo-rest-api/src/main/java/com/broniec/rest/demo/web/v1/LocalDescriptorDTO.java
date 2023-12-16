package com.broniec.rest.demo.web.v1;

import lombok.Builder;
import lombok.experimental.FieldNameConstants;

@Builder
@FieldNameConstants
record LocalDescriptorDTO (
        Long id,
        String sourceSystem,
        String localIdentifier
) { }
