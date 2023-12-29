package com.broniec.rest.demo.domain;

import java.time.LocalDate;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.broniec.rest.demo.UnitTest;
import jakarta.transaction.Transactional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;


class AuthorFacadeUpdateAuthorTest extends UnitTest {

    @Autowired
    private AuthorFacade authorFacade;

    @Autowired
    private AuthorTestUtils authorTestUtils;

    @Test
    @Transactional
    public void shouldUpdateFirstName() {
        //given
        var author = authorTestUtils.buildValidRandomAuthor();
        var registeredAuthor = authorFacade.saveAuthor(author);
        var updateReference = registeredAuthor.withFirstName("newFirstName");
        //when
        var updatedAuthor = authorFacade.updateAuthor(registeredAuthor.getId(), updateReference);
        //then
        assertThat(updatedAuthor.getFirstName(), is("newFirstName"));
    }

    @Test
    @Transactional
    public void shouldUpdateLastName() {
        //given
        var author = authorTestUtils.buildValidRandomAuthor();
        var registeredAuthor = authorFacade.saveAuthor(author);
        var updateReference = registeredAuthor.withLastName("newLastName");
        //when
        var updatedAuthor = authorFacade.updateAuthor(registeredAuthor.getId(), updateReference);
        //then
        assertThat(updatedAuthor.getLastName(), is("newLastName"));
    }

    @Test
    @Transactional
    public void shouldUpdateDateOfBirth() {
        //given
        var author = authorTestUtils.buildValidRandomAuthor();
        var registeredAuthor = authorFacade.saveAuthor(author);
        var updateReference = registeredAuthor.withDateOfBirth(LocalDate.of(1911, 1, 1));
        //when
        var updatedAuthor = authorFacade.updateAuthor(registeredAuthor.getId(), updateReference);
        //then
        assertThat(updatedAuthor.getDateOfBirth(), is(LocalDate.of(1911, 1, 1)));
    }

    @Test
    @Transactional
    public void shouldUpdateDateOfDeath() {
        //given
        var author = authorTestUtils.buildValidRandomAuthor();
        var registeredAuthor = authorFacade.saveAuthor(author);
        var updateReference = registeredAuthor.withDateOfDeath(LocalDate.of(2000, 1, 1));
        //when
        var updatedAuthor = authorFacade.updateAuthor(registeredAuthor.getId(), updateReference);
        //then
        assertThat(updatedAuthor.getDateOfDeath(), is(LocalDate.of(2000, 1, 1)));
    }

    @Test
    @Transactional
    public void shouldUpdateLocalDescriptor() {
        //given
        var author = authorTestUtils.buildValidRandomAuthor();
        var registeredAuthor = authorFacade.saveAuthor(author);
        var registeredDescriptor = registeredAuthor.getLocalDescriptor().stream().findAny().get();
        var updateReference = registeredAuthor.withLocalDescriptor(
                Set.of(
                        LocalDescriptor.builder()
                                .id(registeredAuthor.getId())
                                .localIdentifier("123-abcd")
                                .sourceSystem("data-source-1")
                                .build()
                )
        );
        //when
        var updatedAuthor = authorFacade.updateAuthor(registeredAuthor.getId(), updateReference);
        //then
        assertThat(updatedAuthor.getLocalDescriptor(), hasSize(1));
        assertThat(updatedAuthor.getLocalDescriptor(), hasItem(allOf(
                        hasProperty("id", is(registeredDescriptor.getId())),
                        hasProperty("localIdentifier", is("123-abcd")),
                        hasProperty("sourceSystem", is("data-source-1")))
                )
        );
    }

    @Test
    @Transactional
    public void shouldReplaceLocalDescriptorDueToEmptyId() {
        //given
        var author = authorTestUtils.buildValidRandomAuthor();
        var registeredAuthor = authorFacade.saveAuthor(author);
        var registeredDescriptor = registeredAuthor.getLocalDescriptor().stream().findAny().get();
        var updateReference = registeredAuthor.withLocalDescriptor(
                Set.of(
                        LocalDescriptor.builder()
                                .id(null) // null id, so new LD entity will be saved
                                .localIdentifier("123-abcd")
                                .sourceSystem("data-source-1")
                                .build()
                )
        );
        //when
        var updatedAuthor = authorFacade.updateAuthor(registeredAuthor.getId(), updateReference);
        //then
        assertThat(updatedAuthor.getLocalDescriptor(), hasSize(1));
        assertThat(updatedAuthor.getLocalDescriptor(), hasItem(hasProperty("id", not(registeredDescriptor.getId()))));
    }

    @Test
    @Transactional
    public void shouldReplaceLocalDescriptorDueToInvalidId() {
        //given
        var author = authorTestUtils.buildValidRandomAuthor();
        var registeredAuthor = authorFacade.saveAuthor(author);
        var registeredDescriptor = registeredAuthor.getLocalDescriptor().stream().findAny().get();
        var updateReference = registeredAuthor.withLocalDescriptor(
                Set.of(
                        LocalDescriptor.builder()
                                .id(Long.MAX_VALUE) // invalid id, so new LD entity will be saved
                                .localIdentifier("123-abcd")
                                .sourceSystem("data-source-1")
                                .build()
                )
        );
        //when
        var updatedAuthor = authorFacade.updateAuthor(registeredAuthor.getId(), updateReference);
        //then
        assertThat(updatedAuthor.getLocalDescriptor(), hasSize(1));
        assertThat(updatedAuthor.getLocalDescriptor(), hasItem(hasProperty("id", not(registeredDescriptor.getId()))));
    }


}
