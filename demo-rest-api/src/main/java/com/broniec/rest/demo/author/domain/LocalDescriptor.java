package com.broniec.rest.demo.author.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocalDescriptor {

    private LocalDescriptorId id;
    private String sourceSystem;
    private String localIdentifier;


}
