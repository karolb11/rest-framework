package com.broniec.rest.demo.author.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Author {

    private AuthorId id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private LocalDate dateOfDeath;
    private Set<LocalDescriptor> localDescriptor;

    @JsonCreator
    public Author() {
        localDescriptor = new HashSet<>();
    }

}
