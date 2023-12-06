package com.broniec.rest.demo.author.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.broniec.rest.famework.Entity;
import com.broniec.rest.famework.UpdateHelper;
import jakarta.persistence.CascadeType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@jakarta.persistence.Entity
@Setter
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

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<LocalDescriptor> localDescriptor = new HashSet<>();

    public void addLocalDescriptor(LocalDescriptor localDescriptor) {
        this.localDescriptor.add(localDescriptor);
        localDescriptor.setAuthor(this);
    }

    public void update(Author reference) {
        this.firstName = reference.firstName;
        this.lastName = reference.lastName;
        this.dateOfBirth = reference.dateOfBirth;
        this.dateOfDeath = reference.dateOfDeath;
        new UpdateHelper().updateCollection(localDescriptor, reference.getLocalDescriptor());
    }
}
