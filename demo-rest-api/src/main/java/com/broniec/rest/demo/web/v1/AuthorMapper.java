package com.broniec.rest.demo.web.v1;

import org.springframework.stereotype.Component;

import com.broniec.rest.demo.domain.Author;
import com.broniec.rest.famework.Objects;
import lombok.RequiredArgsConstructor;

import static com.broniec.rest.famework.Objects.optionalCollection;

@Component
@RequiredArgsConstructor
class AuthorMapper {

    private final LocalDescriptorMapper localDescriptorMapper;
    private final OpusMapper opusMapper;

    public Author toAuthor(AuthorDTO authorDTO) {
        var author = Author.builder()
                .id(authorDTO.id())
                .firstName(authorDTO.firstName())
                .lastName(authorDTO.lastName())
                .dateOfBirth(authorDTO.dateOfBirth())
                .dateOfDeath(authorDTO.dateOfDeath())
                .build();
        optionalCollection(authorDTO::localDescriptor).stream()
                .map(localDescriptorMapper::toLocalDescriptor)
                .forEach(author::addLocalDescriptor);
        return author;
    }

    public AuthorDTO toAuthorDTO(Author author) {
        var localDescriptor = Objects.optionalCollection(author::getLocalDescriptor).stream()
                .map(localDescriptorMapper::toLocalDescriptorDTO)
                .toList();
        var opus = Objects.optionalCollection(author::getOpus).stream()
                .map(opusMapper::toOpusDTO)
                .toList();
        return AuthorDTO.builder()
                .id(author.getId())
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .dateOfBirth(author.getDateOfBirth())
                .dateOfDeath(author.getDateOfDeath())
                .localDescriptor(localDescriptor)
                .opus(opus)
                .build();
    }

}
