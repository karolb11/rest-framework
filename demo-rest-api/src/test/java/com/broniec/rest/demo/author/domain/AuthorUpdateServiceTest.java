package com.broniec.rest.demo.author.domain;

import java.time.LocalDate;
import java.util.Set;

import org.assertj.core.util.Sets;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.broniec.rest.demo.UnitTest;
import jakarta.transaction.Transactional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;


class AuthorFacadeTest extends UnitTest {

    @Autowired
    private AuthorFacade authorFacade;

    @Test
    @Transactional
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
        assertThat(foundAuthor, Matchers.is(foundAuthor));
    }

    @Test
    @Transactional
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
        var savedAuthorId = authorFacade.saveAuthor(authorToBeRegistered).getId();
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
        authorFacade.updateAuthor(savedAuthorId, updateReference);
        //then
        var updatedAuthor = authorFacade.findAuthor(savedAuthorId).get();
        assertThat(updatedAuthor.getFirstName(), Matchers.equalTo(updateReference.getFirstName()));
        assertThat(updatedAuthor.getLastName(), Matchers.equalTo(updateReference.getLastName()));
        assertThat(updatedAuthor.getDateOfBirth(), Matchers.equalTo(updateReference.getDateOfBirth()));
        assertThat(updatedAuthor.getLocalDescriptor(), Matchers.hasSize(1));
        assertThat(updatedAuthor.getLocalDescriptor(), Matchers.hasItem(Matchers.allOf(
                hasProperty("sourceSystem", is("open-library")),
                hasProperty("localIdentifier", is("auth2"))
        )));
    }

}
