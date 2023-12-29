package com.broniec.rest.demo.utils;

import java.time.LocalDate;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class RandomDate {

    private final Random random;

    public RandomDate() {
        random = new Random();
    }

    public LocalDate generateRandomDateBetween(LocalDate startDate, LocalDate endDate) {
        // Calculate the range of days between the start and end dates
        long startEpochDay = startDate.toEpochDay();
        long endEpochDay = endDate.toEpochDay();
        long range = endEpochDay - startEpochDay;

        // Generate a random number within the range
        long randomDays = (long) (random.nextDouble() * range) + startEpochDay;

        // Create a new LocalDate from the random number of days
        return LocalDate.ofEpochDay(randomDays);
    }


}
