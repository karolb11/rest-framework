package com.broniec.rest.demo.author.web.v1;

import java.time.LocalDate;

import lombok.Builder;
import lombok.experimental.FieldNameConstants;

@Builder
@FieldNameConstants
record ArticleDTO(
        Long id,
        String title,
        LocalDate publicationDate,
        String periodicalName
) implements OpusDTO {
}
