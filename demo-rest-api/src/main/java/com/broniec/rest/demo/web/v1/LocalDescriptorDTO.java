package com.broniec.rest.demo.web.v1;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldNameConstants;

@Builder(access = AccessLevel.PACKAGE)
@FieldNameConstants(level = AccessLevel.PACKAGE)
record LocalDescriptorDTO (
        Long id,
        String sourceSystem,
        String localIdentifier
) { }
