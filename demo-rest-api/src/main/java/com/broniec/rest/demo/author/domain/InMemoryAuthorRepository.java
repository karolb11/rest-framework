package com.broniec.rest.demo.author.domain;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
class InMemoryAuthorRepository implements AuthorRepository {

    private final Map<AuthorId, Author> authors;

    InMemoryAuthorRepository() {
        this.authors = new ConcurrentHashMap<>();
    }

    @Override
    public Author save(Author author) {
        if (isNull(author.getId())) {
            author.setId(new AuthorId(UUID.randomUUID()));
        }
        author.getLocalDescriptor().forEach(ld -> {
            if (isNull(ld.getId())) {
                ld.setId(new LocalDescriptorId(UUID.randomUUID()));
            }
        });
        authors.put(author.getId(), author);
        return author;
    }

    @Override
    public Optional<Author> findById(AuthorId authorId) {
        return Optional.ofNullable(authors.get(authorId));
    }

    @Override
    public Optional<Author> findByFirstNameAndLastName(String firstName, String lastName) {
        return authors.values().stream()
                .filter(author -> Objects.equals(author.getFirstName(), firstName) && Objects.equals(author.getLastName(), lastName))
                .findAny();
    }

}
