package com.broniec.rest.demo.author.domain;

import java.time.LocalDate;
import java.util.Set;

import org.assertj.core.util.Sets;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.broniec.rest.demo.UnitTest;

import static org.assertj.core.api.Assertions.assertThat;

class AuthorFacadeTest extends UnitTest {

    @Autowired
    private AuthorFacade authorFacade;

    @Test
    public void shouldSaveAuthor() {
        //given
        var authorToBeRegistered = Author.builder()
                .firstName("Henryk")
                .lastName("Sienkiewicz")
                .dateOfBirth(LocalDate.of(1990, 10, 5))
                .localDescriptor(Set.of(
                        LocalDescriptor.builder()
                                .localIdentifier("auth1")
                                .sourceSystem("open-library")
                                .build()
                ))
                .build();
        //when
        var savedAuthor = authorFacade.saveAuthor(authorToBeRegistered);
        //then
        var foundAuthor = authorFacade.findAuthor(savedAuthor.getId());
        assertThat(foundAuthor).contains(savedAuthor);
    }

    @Test
    public void shouldUpdateAuthor() {
        //given
        var authorToBeRegistered = Author.builder()
                .firstName("Henryk")
                .lastName("Sienkiewicz")
                .dateOfBirth(LocalDate.of(1990, 10, 5))
                .localDescriptor(Sets.newLinkedHashSet(
                        LocalDescriptor.builder()
                                .localIdentifier("auth1")
                                .sourceSystem("open-library")
                                .build()
                ))
                .build();
        var savedAuthor = authorFacade.saveAuthor(authorToBeRegistered);
        //given
        var updateReference = Author.builder()
                .firstName("Henryk2")
                .lastName("Sienkiewicz2")
                .dateOfBirth(LocalDate.of(1900, 10, 5))
                .localDescriptor(Sets.newLinkedHashSet(
                        LocalDescriptor.builder()
                                .localIdentifier("auth2")
                                .sourceSystem("open-library")
                                .build()
                ))
                .build();
        //when
        authorFacade.updateAuthor(savedAuthor.getId(), updateReference);
        //then
        assertThat(savedAuthor.getFirstName()).isEqualTo(updateReference.getFirstName());
        assertThat(savedAuthor.getLastName()).isEqualTo(updateReference.getLastName());
        assertThat(savedAuthor.getDateOfBirth()).isEqualTo(updateReference.getDateOfBirth());
        assertThat(savedAuthor.getLocalDescriptor()).hasSize(1);
    }

}
