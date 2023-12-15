package com.broniec.rest.demo.author.web.v1;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type",
        defaultImpl = UnknownOpusDTO.class
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = BookDTO.class, name = OpusDTO.TYPE_BOOK),
        @JsonSubTypes.Type(value = ArticleDTO.class, name = OpusDTO.TYPE_ARTICLE)
})
sealed interface OpusDTO permits ArticleDTO, BookDTO, UnknownOpusDTO {

    String TYPE_BOOK = "book";
    String TYPE_ARTICLE = "article";

    Long id();

    String title();

    LocalDate publicationDate();

}
