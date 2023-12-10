package com.broniec.rest.demo.author.domain;

import java.time.LocalDate;
import java.util.Set;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.broniec.rest.demo.UnitTest;
import jakarta.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;


class AuthorFacadeSaveAuthorTest extends UnitTest {

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

}
