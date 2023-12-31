package com.broniec.rest.demo.web.v1;

import java.time.LocalDate;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.broniec.rest.demo.UnitTest;
import com.broniec.rest.famework.validator.ConstraintViolation;
import com.broniec.rest.famework.validator.ValidationContext;

import static org.assertj.core.api.Assertions.assertThat;


class AuthorDTOValidationTest extends UnitTest {

    @Autowired
    private AuthorValidatorFactory authorValidatorFactory;

    @Test
    void shouldValidateValidObject() {
        //given
        var validator = authorValidatorFactory.buildAuthorDTOValidator();
        var author = validAuthorDTOBuilder().build();
        //when
        var result = validator.validate(author, ValidationContext.create());
        //then
        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnViolationForEmptyFirstName() {
        //given
        var validator = authorValidatorFactory.buildAuthorDTOValidator();
        var author = validAuthorDTOBuilder()
                .firstName(null)
                .build();
        //when
        var result = validator.validate(author, ValidationContext.create());
        //then
        var expectedViolation = new ConstraintViolation("firstName", "Mandatory field");
        assertThat(result).containsExactly(expectedViolation);
    }

    @Test
    void shouldReturnViolationForTooShortFirstName() {
        //given
        var validator = authorValidatorFactory.buildAuthorDTOValidator();
        var author = validAuthorDTOBuilder()
                .firstName("A")
                .build();
        //when
        var result = validator.validate(author, ValidationContext.create());
        //then
        var expectedViolation = new ConstraintViolation("firstName", "Min length is 3");
        assertThat(result).containsExactly(expectedViolation);
    }

    @Test
    void shouldReturnViolationForTooLongFirstName() {
        //given
        var validator = authorValidatorFactory.buildAuthorDTOValidator();
        var author = validAuthorDTOBuilder()
                .firstName("A" + RandomStringUtils.random(500))
                .build();
        //when
        var result = validator.validate(author, ValidationContext.create());
        //then
        var expectedViolation = new ConstraintViolation("firstName", "Max length is 100");
        assertThat(result).containsExactly(expectedViolation);
    }

    @Test
    void shouldReturnViolationForNonCapitalFirstName() {
        //given
        var validator = authorValidatorFactory.buildAuthorDTOValidator();
        var author = validAuthorDTOBuilder()
                .firstName("firstName")
                .build();
        //when
        var result = validator.validate(author, ValidationContext.create());
        //then
        var expectedViolation = new ConstraintViolation("firstName", "Must start with capital letter");
        assertThat(result).containsExactly(expectedViolation);
    }

    @Test
    void shouldReturnViolationForEmptyDateOfBirth() {
        //given
        var validator = authorValidatorFactory.buildAuthorDTOValidator();
        var author = validAuthorDTOBuilder()
                .dateOfBirth(null)
                .build();
        //when
        var result = validator.validate(author, ValidationContext.create());
        //then
        var expectedViolation = new ConstraintViolation("dateOfBirth", "Mandatory field");
        assertThat(result).containsExactly(expectedViolation);
    }

    @Test
    void shouldReturnViolationForFutureDateOfBirth() {
        //given
        var validator = authorValidatorFactory.buildAuthorDTOValidator();
        var author = validAuthorDTOBuilder()
                .dateOfBirth(LocalDate.of(2001, 1, 10))
                .dateOfDeath(null)
                .build();
        //when
        var result = validator.validate(author, ValidationContext.create());
        //then
        var expectedViolation = new ConstraintViolation("dateOfBirth", "Date must be past");
        assertThat(result).containsExactly(expectedViolation);
    }

    private AuthorDTO.AuthorDTOBuilder validAuthorDTOBuilder() {
        return AuthorDTO.builder()
                .firstName("Henryk")
                .lastName("Sienkiewicz")
                .dateOfBirth(LocalDate.of(1846, 5, 5))
                .dateOfDeath(LocalDate.of(1916, 11, 15));
    }

}
