package com.broniec.rest.demo.web.v1;

import org.springframework.stereotype.Component;

import com.broniec.rest.demo.domain.Article;
import com.broniec.rest.demo.domain.Book;
import com.broniec.rest.demo.domain.Opus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
class OpusMapper {
    public Opus toOpus(OpusDTO opusDTO) {
        return switch (opusDTO) {
            case ArticleDTO article -> buildArticle(article);
            case BookDTO book -> buildBook(book);
            case UnknownOpusDTO unknown -> throw new IllegalArgumentException("OpusDTO can not be " + UnknownOpusDTO.class.getName());
        };
    }

    private Book buildBook(BookDTO book) {
        return Book.builder()
                .id(book.id())
                .title(book.title())
                .publicationDate(book.publicationDate())
                .dedication(book.dedication())
                .build();
    }

    private Article buildArticle(ArticleDTO article) {
        return Article.builder()
                .id(article.id())
                .periodicalName(article.periodicalName())
                .title(article.title())
                .publicationDate(article.publicationDate())
                .build();
    }

    public OpusDTO toOpusDTO(Opus opus) {
        return switch (opus) {
            case Article article -> buildArticleDTO(article);
            case Book book -> buildBookDTO(book);
            default -> throw new IllegalArgumentException("Opus can not be: " + opus.getClass().getName());
        };
    }

    private ArticleDTO buildArticleDTO(Article article) {
        return ArticleDTO.builder()
                .id(article.getId())
                .title(article.getTitle())
                .publicationDate(article.getPublicationDate())
                .periodicalName(article.getPeriodicalName())
                .build();
    }

    private BookDTO buildBookDTO(Book book) {
        return BookDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .publicationDate(book.getPublicationDate())
                .dedication(book.getDedication())
                .build();
    }
}
