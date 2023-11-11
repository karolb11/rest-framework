package com.broniec.rest.demo;

import java.time.LocalDate;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootTest
public class UnitTest {

    @Bean
    public TimeService timeService() {
        return new TimeService() {
            @Override
            public LocalDate currentDate() {
                return LocalDate.of(2000, 1, 1);
            }
        };
    }

}
