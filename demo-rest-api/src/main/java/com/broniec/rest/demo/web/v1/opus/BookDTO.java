package com.broniec.rest.demo.web.v1.opus;

import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldNameConstants;

@Builder(access = AccessLevel.PACKAGE)
@FieldNameConstants(level = AccessLevel.PACKAGE)
record BookDTO(
        Long id,
        String title,
        LocalDate publicationDate,
        String dedication
) implements OpusDTO {
}
