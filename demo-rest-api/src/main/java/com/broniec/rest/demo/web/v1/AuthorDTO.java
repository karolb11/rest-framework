package com.broniec.rest.demo.web.v1;

import java.time.LocalDate;
import java.util.List;

import com.broniec.rest.demo.web.v1.opus.OpusDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldNameConstants;

@Builder(access = AccessLevel.PACKAGE)
@FieldNameConstants(level = AccessLevel.PACKAGE)
record AuthorDTO(
        Long id,
        String firstName,
        String lastName,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate dateOfBirth,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate dateOfDeath,
        List<LocalDescriptorDTO> localDescriptor,
        List<OpusDTO> opus
) { }
