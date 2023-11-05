package com.broniec.rest.demo;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

@SpringBootTest
class DemoRestApiApplicationTests {

    @Test
    void contextLoads() {
    }

    @SneakyThrows
    @Test
    void objectMapperTest() {
        var objectMapper = new ObjectMapper();
        objectMapper.writeValueAsString(LocalDate.now());
        System.out.println();
    }

}
