package com.broniec.rest.demo.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.broniec.rest.demo.UnitTest;
import jakarta.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;


class AuthorFacadeFindAuthorTest extends UnitTest {

    @Autowired
    private AuthorFacade authorFacade;

    @Autowired
    private AuthorTestUtils authorTestUtils;

    @Test
    @Transactional
    public void shouldFindAuthorById() {
        //given
        var authorToBeRegistered = authorTestUtils.buildValidRandomAuthor();
        var savedAuthor = authorFacade.saveAuthor(authorToBeRegistered);
        //when
        var foundAuthor = authorFacade.findAuthor(savedAuthor.getId());
        //then
        assertThat(foundAuthor).isNotEmpty();
    }



}
