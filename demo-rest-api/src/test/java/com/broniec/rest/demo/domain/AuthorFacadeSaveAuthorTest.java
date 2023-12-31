package com.broniec.rest.demo.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.broniec.rest.demo.UnitTest;
import jakarta.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;


class AuthorFacadeSaveAuthorTest extends UnitTest {

    @Autowired
    private AuthorFacade authorFacade;

    @Autowired
    private AuthorTestUtils authorTestUtils;

    @Test
    @Transactional
    public void shouldSaveAuthor() {
        //given
        var authorToBeRegistered = authorTestUtils.buildValidRandomAuthor();
        //when
        var savedAuthor = authorFacade.saveAuthor(authorToBeRegistered);
        //then
        assertThat(savedAuthor.getId()).isNotNull();
    }



}
