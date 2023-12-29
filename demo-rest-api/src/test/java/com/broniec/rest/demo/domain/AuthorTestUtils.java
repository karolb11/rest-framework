package com.broniec.rest.demo.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import com.broniec.rest.demo.TimeService;
import com.broniec.rest.demo.utils.RandomDate;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class AuthorTestUtils {

    private final RandomDate randomDate;
    private final TimeService timeService;

    public Author buildValidRandomAuthor() {
        var randomBirthDate = randomDate.generateRandomDateBetween(
                LocalDate.of(1900, 1, 1),
                LocalDate.of(1950, 12, 31)
        );
        var randomDeathDate = randomDate.generateRandomDateBetween(
                randomBirthDate.plusYears(20),
                timeService.currentDate()
        );
        return Author.builder()
                .firstName(randomString("firstName"))
                .lastName(randomString("lastName"))
                .dateOfBirth(randomBirthDate)
                .dateOfDeath(randomDeathDate)
                .localDescriptor(buildLocalDescriptorSet(1))
                .build();
    }

    private Set<LocalDescriptor> buildLocalDescriptorSet(int capacity) {
        return IntStream.range(0, capacity).mapToObj(i -> LocalDescriptor.builder()
                .localIdentifier(randomString("localIdentifier"))
                .sourceSystem(randomString("sourceSystem"))
                .build()
        ).collect(Collectors.toCollection(HashSet::new));
    }

    private String randomString(String prefix) {
        return prefix + "-" +RandomStringUtils.randomAlphabetic(5);
    }

}
