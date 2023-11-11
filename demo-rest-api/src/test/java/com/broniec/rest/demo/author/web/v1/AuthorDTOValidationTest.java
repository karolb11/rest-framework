package com.broniec.rest.demo.author.web.v1;

import java.time.LocalDate;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.broniec.rest.demo.UnitTest;
import com.broniec.rest.famework.validator.ConstraintViolation;

import static org.assertj.core.api.Assertions.assertThat;


class AuthorDTOValidationTest extends UnitTest {

    @Autowired
    private ValidatorFactory validatorFactory;

    @Test
    void shouldValidateValidObject() {
        //given
        var validator = validatorFactory.buildAuthorDTOValidator();
        var author = validAuthorDTOBuilder().build();
        //when
        var result = validator.validate(author);
        //then
        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnViolationForEmptyFirstName() {
        //given
        var validator = validatorFactory.buildAuthorDTOValidator();
        var author = validAuthorDTOBuilder()
                .firstName(null)
                .build();
        //when
        var result = validator.validate(author);
        //then
        assertThat(result).containsExactly(
                new ConstraintViolation("firstName", "Mandatory field"),
                new ConstraintViolation("firstName", "Min length is 3")
        );
    }

    @Test
    void shouldReturnViolationForTooLongFirstName() {
        //given
        var validator = validatorFactory.buildAuthorDTOValidator();
        var author = validAuthorDTOBuilder()
                .firstName(RandomStringUtils.random(500))
                .build();
        //when
        var result = validator.validate(author);
        //then
        assertThat(result).containsExactly(
                new ConstraintViolation("firstName", "Max length is 100")
        );
    }

    @Test
    void shouldReturnViolationForEmptyDateOfBirth() {
        //given
        var validator = validatorFactory.buildAuthorDTOValidator();
        var author = validAuthorDTOBuilder()
                .dateOfBirth(null)
                .build();
        //when
        var result = validator.validate(author);
        //then
        assertThat(result).containsExactly(
                new ConstraintViolation("dateOfBirth", "Mandatory field")
        );
    }

    @Test
    void shouldReturnViolationForFutureDateOfBirth() {
        //given
        var validator = validatorFactory.buildAuthorDTOValidator();
        var author = validAuthorDTOBuilder()
                .dateOfBirth(LocalDate.of(2001, 1, 10))
                .dateOfDeath(null)
                .build();
        //when
        var result = validator.validate(author);
        //then
        assertThat(result).containsExactly(
                new ConstraintViolation("dateOfBirth", "Date must be past")
        );
    }

    private AuthorDTO.AuthorDTOBuilder validAuthorDTOBuilder() {
        return AuthorDTO.builder()
                .firstName("Henryk")
                .lastName("Sienkiewicz")
                .dateOfBirth(LocalDate.of(1846, 5, 5))
                .dateOfDeath(LocalDate.of(1916, 11, 15));
    }

}
