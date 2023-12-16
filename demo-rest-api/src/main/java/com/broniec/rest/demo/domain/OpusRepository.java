package com.broniec.rest.demo.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OpusRepository extends JpaRepository<Opus, Long> {

    List<Opus> findByTitleAndAuthor_Id(String title, long authorId);

}
