package com.broniec.rest.demo.web.v1.opus;

import java.util.Collection;

import org.springframework.stereotype.Component;

import com.broniec.rest.demo.domain.Opus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class OpusMapper {

    private final Collection<IOpusMapper> mappers;

    public Opus toEntity(OpusDTO opusDTO) {
        var mapper = dispatchMapper(opusDTO);
        return mapper.toEntity(opusDTO);
    }

    public OpusDTO toDTO(Opus opus) {
        var mapper = dispatchMapper(opus);
        return mapper.toDTO(opus);
    }

    private IOpusMapper dispatchMapper(OpusDTO opusDTO) {
        return mappers.stream().filter(mapper -> mapper.supports(opusDTO))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("Unable to dispatch IOpusMapper for " + opusDTO.getClass().getName()));
    }

    private IOpusMapper dispatchMapper(Opus opus) {
        return mappers.stream().filter(mapper -> mapper.supports(opus))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("Unable to dispatch IOpusMapper for " + opus.getClass().getName()));
    }

}