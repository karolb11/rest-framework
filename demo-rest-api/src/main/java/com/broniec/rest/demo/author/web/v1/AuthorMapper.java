package com.broniec.rest.demo.author.web.v1;

import org.springframework.stereotype.Component;

import com.broniec.rest.demo.author.domain.Author;

@Component
class AuthorMapper {

    public Author toAuthor(AuthorDTO authorDTO) {
        return Author.builder()
                .firstName(authorDTO.firstName())
                .lastName(authorDTO.lastName())
                .dateOfBirth(authorDTO.dateOfBirth())
                .dateOfDeath(authorDTO.dateOfDeath())
                .build();
    }

    public AuthorDTO toAuthorDTO(Author author) {
        return AuthorDTO.builder()
                .authorId(author.getId().getValue())
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .dateOfBirth(author.getDateOfBirth())
                .dateOfDeath(author.getDateOfDeath())
                .build();
    }

}
