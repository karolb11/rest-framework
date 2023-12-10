package com.broniec.rest.demo.author.domain;

import com.broniec.rest.famework.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@jakarta.persistence.Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocalDescriptor implements Entity<LocalDescriptor, Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sourceSystem;
    private String localIdentifier;

    @Override
    public void update(LocalDescriptor reference) {
        this.sourceSystem = reference.sourceSystem;
        this.localIdentifier = reference.localIdentifier;
    }
}
