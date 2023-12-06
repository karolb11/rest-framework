package com.broniec.rest.demo.author.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.broniec.rest.famework.Entity;
import com.broniec.rest.famework.UpdateHelper;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
public class Author implements Entity<Author> {

    @Setter
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

    public void update(Author reference) {
        this.firstName = reference.firstName;
        this.lastName = reference.lastName;
        this.dateOfBirth = reference.dateOfBirth;
        this.dateOfDeath = reference.dateOfDeath;
        new UpdateHelper().updateCollection(localDescriptor, reference.getLocalDescriptor());
    }
}
