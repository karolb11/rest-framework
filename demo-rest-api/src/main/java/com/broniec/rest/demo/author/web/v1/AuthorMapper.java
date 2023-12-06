package com.broniec.rest.demo.author.web.v1;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.broniec.rest.demo.author.domain.Author;
import com.broniec.rest.demo.author.domain.AuthorId;
import com.broniec.rest.famework.Objects;
import lombok.RequiredArgsConstructor;

import static com.broniec.rest.famework.Objects.optionalCollection;

@Component
@RequiredArgsConstructor
class AuthorMapper {

    private final LocalDescriptorMapper localDescriptorMapper;

    public Author toAuthor(AuthorDTO authorDTO) {
        var id = Optional.ofNullable(authorDTO.id())
                .map(AuthorId::new)
                .orElse(null);
        var localDescriptor = optionalCollection(authorDTO::localDescriptor).stream()
                .map(localDescriptorMapper::toLocalDescriptor)
                .collect(Collectors.toSet());
        return Author.builder()
                .id(id)
                .firstName(authorDTO.firstName())
                .lastName(authorDTO.lastName())
                .dateOfBirth(authorDTO.dateOfBirth())
                .dateOfDeath(authorDTO.dateOfDeath())
                .localDescriptor(localDescriptor)
                .build();
    }

    public AuthorDTO toAuthorDTO(Author author) {
        var localDescriptor = Objects.optionalCollection(author::getLocalDescriptor).stream()
                .map(localDescriptorMapper::toLocalDescriptorDTO)
                .toList();
        return AuthorDTO.builder()
                .id(author.getId().getValue())
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .dateOfBirth(author.getDateOfBirth())
                .dateOfDeath(author.getDateOfDeath())
                .localDescriptor(localDescriptor)
                .build();
    }

}
