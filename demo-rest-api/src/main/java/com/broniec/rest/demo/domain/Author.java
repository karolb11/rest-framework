package com.broniec.rest.demo.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.broniec.rest.famework.Entity;
import com.broniec.rest.famework.UpdateHelper;
import jakarta.persistence.CascadeType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;

@jakarta.persistence.Entity
@With
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Author implements Entity<Author, Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private LocalDate dateOfDeath;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "author_id")
    @Builder.Default
    private Set<LocalDescriptor> localDescriptor = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "author_id")
    @Builder.Default
    private Set<Opus> opus = new HashSet<>();

    public void addLocalDescriptor(LocalDescriptor localDescriptor) {
        this.localDescriptor.add(localDescriptor);
    }

    public void addOpus(Opus opus) {
        this.opus.add(opus);
    }

    public void update(Author reference) {
        this.firstName = reference.firstName;
        this.lastName = reference.lastName;
        this.dateOfBirth = reference.dateOfBirth;
        this.dateOfDeath = reference.dateOfDeath;
        new UpdateHelper().updateCollection(localDescriptor, reference.getLocalDescriptor());
    }

    @Override
    public Author copy() {
        var localDescriptor = this.localDescriptor.stream()
                .map(Entity::copy)
                .collect(Collectors.toSet());

        return Author.builder()
                .firstName(firstName)
                .lastName(lastName)
                .dateOfBirth(dateOfBirth)
                .dateOfDeath(dateOfDeath)
                .localDescriptor(localDescriptor)
                .build();
    }
}
