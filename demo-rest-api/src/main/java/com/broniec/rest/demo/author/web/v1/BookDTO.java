package com.broniec.rest.demo.author.web.v1;

import java.time.LocalDate;

import lombok.Builder;
import lombok.experimental.FieldNameConstants;

@Builder
@FieldNameConstants
record BookDTO(
        Long id,
        String title,
        LocalDate publicationDate
) implements OpusDTO {
}
