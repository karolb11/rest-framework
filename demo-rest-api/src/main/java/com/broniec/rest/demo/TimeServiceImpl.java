package com.broniec.rest.demo;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

@Component
class TimeServiceImpl implements TimeService {

    @Override
    public LocalDate currentDate() {
        return LocalDate.now();
    }

}
