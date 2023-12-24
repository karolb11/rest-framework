package com.broniec.rest.demo.web.v1.opus;

import com.broniec.rest.demo.domain.Opus;

interface IOpusMapper {
    boolean supports(OpusDTO opusDTO);
    boolean supports(Opus opus);

    Opus toEntity(OpusDTO opusDTO);
    OpusDTO toDTO(Opus opus);
}