package com.broniec.rest.demo.author.web.v1;

import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.experimental.FieldNameConstants;

@Builder
@FieldNameConstants
record AuthorDTO (
        UUID authorId,
        String firstName,
        String lastName,

        @JsonFormat(pattern="yyyy-MM-dd")
        LocalDate dateOfBirth,

        @JsonFormat(pattern="yyyy-MM-dd")
        LocalDate dateOfDeath
) { }
