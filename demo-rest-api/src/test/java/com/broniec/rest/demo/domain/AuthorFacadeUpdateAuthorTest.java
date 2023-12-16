package com.broniec.rest.demo.domain;

import java.time.LocalDate;

import org.assertj.core.util.Sets;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.broniec.rest.demo.UnitTest;
import jakarta.transaction.Transactional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;


class AuthorFacadeUpdateAuthorTest extends UnitTest {

    @Autowired
    private AuthorFacade authorFacade;

    @Test
    @Transactional
    public void shouldUpdateAuthorAndDescriptor() {
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
        var savedAuthorId = savedAuthor.getId();
        var savedDescriptorId = savedAuthor.getLocalDescriptor().stream().map(LocalDescriptor::getId).findAny().get();
        var updateReference = Author.builder()
                .firstName("Henryk2")
                .lastName("Sienkiewicz2")
                .dateOfBirth(LocalDate.of(1900, 10, 5))
                .dateOfDeath(LocalDate.of(1990, 10, 5))
                .localDescriptor(Sets.newLinkedHashSet(
                        LocalDescriptor.builder()
                                .id(savedDescriptorId)
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
        assertThat(updatedAuthor.getDateOfDeath(), Matchers.equalTo(updateReference.getDateOfDeath()));
        assertThat(updatedAuthor.getLocalDescriptor(), Matchers.hasSize(1));
        assertThat(updatedAuthor.getLocalDescriptor(), Matchers.hasItem(Matchers.allOf(
                hasProperty("id", is(savedDescriptorId)),
                hasProperty("sourceSystem", is("open-library")),
                hasProperty("localIdentifier", is("auth2"))
        )));
    }

    @Test
    @Transactional
    public void shouldUpdateAuthorAndCreateNewDescriptor() {
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
        var savedAuthorId = savedAuthor.getId();
        var savedDescriptorId = savedAuthor.getLocalDescriptor().stream().map(LocalDescriptor::getId).findAny().get();
        var updateReference = Author.builder()
                .firstName("Henryk2")
                .lastName("Sienkiewicz2")
                .dateOfBirth(LocalDate.of(1900, 10, 5))
                .dateOfDeath(LocalDate.of(1990, 10, 5))
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
        assertThat(updatedAuthor.getDateOfDeath(), Matchers.equalTo(updateReference.getDateOfDeath()));
        assertThat(updatedAuthor.getLocalDescriptor(), Matchers.hasSize(1));
        assertThat(updatedAuthor.getLocalDescriptor(), Matchers.hasItem(Matchers.allOf(
                hasProperty("id", not(savedDescriptorId)),
                hasProperty("sourceSystem", is("open-library")),
                hasProperty("localIdentifier", is("auth2"))
        )));
    }

}
