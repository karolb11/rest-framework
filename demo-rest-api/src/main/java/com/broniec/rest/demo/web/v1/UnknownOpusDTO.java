package com.broniec.rest.demo.web.v1;

import java.time.LocalDate;

record UnknownOpusDTO(String type) implements OpusDTO {
    @Override
    public Long id() {
        return null;
    }

    @Override
    public String title() {
        return null;
    }

    @Override
    public LocalDate publicationDate() {
        return null;
    }
}
