package com.broniec.rest.demo.author.domain;

import com.broniec.rest.famework.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@jakarta.persistence.Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocalDescriptor implements Entity<LocalDescriptor, Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sourceSystem;
    private String localIdentifier;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @Override
    public void update(LocalDescriptor reference) {
        this.sourceSystem = reference.sourceSystem;
        this.localIdentifier = reference.localIdentifier;
    }
}
