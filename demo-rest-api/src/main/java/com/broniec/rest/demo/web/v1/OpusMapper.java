package com.broniec.rest.demo.web.v1;

import java.util.Collection;

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

    private final Collection<IOpusMapper> mappers;

    public Opus toEntity(OpusDTO opusDTO) {
        var mapper = dispatchMapper(opusDTO);
        return mapper.toEntity(opusDTO);
    }

    public OpusDTO toDTO(Opus opus) {
        var mapper = dispatchMapper(opus);
        return mapper.toDTO(opus);
    }

    private IOpusMapper dispatchMapper(OpusDTO opusDTO) {
        return mappers.stream().filter(mapper -> mapper.supports(opusDTO))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("Unable to dispatch IOpusMapper for " + opusDTO.getClass().getName()));
    }

    private IOpusMapper dispatchMapper(Opus opus) {
        return mappers.stream().filter(mapper -> mapper.supports(opus))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("Unable to dispatch IOpusMapper for " + opus.getClass().getName()));
    }

    @Component
    private static class BookMapper implements IOpusMapper {

        @Override
        public boolean supports(OpusDTO opusDTO) {
            return BookDTO.class.isAssignableFrom(opusDTO.getClass());
        }

        @Override
        public boolean supports(Opus opus) {
            return Book.class.isAssignableFrom(opus.getClass());
        }

        @Override
        public Opus toEntity(OpusDTO opusDTO) {
            var book = cast(opusDTO);
            return Book.builder()
                    .id(book.id())
                    .title(book.title())
                    .publicationDate(book.publicationDate())
                    .dedication(book.dedication())
                    .build();
        }

        @Override
        public OpusDTO toDTO(Opus opus) {
            var book = cast(opus);
            return BookDTO.builder()
                    .id(book.getId())
                    .title(book.getTitle())
                    .publicationDate(book.getPublicationDate())
                    .dedication(book.getDedication())
                    .build();
        }

        private BookDTO cast(OpusDTO opusDTO) {
            return (BookDTO) opusDTO;
        }

        private Book cast(Opus opus) {
            return (Book) opus;
        }

    }

    @Component
    private static class ArticleMapper implements IOpusMapper {

        @Override
        public boolean supports(OpusDTO opusDTO) {
            return ArticleDTO.class.isAssignableFrom(opusDTO.getClass());
        }

        @Override
        public boolean supports(Opus opus) {
            return Article.class.isAssignableFrom(opus.getClass());
        }

        @Override
        public Opus toEntity(OpusDTO opusDTO) {
            var article = cast(opusDTO);
            return Article.builder()
                    .id(article.id())
                    .periodicalName(article.periodicalName())
                    .title(article.title())
                    .publicationDate(article.publicationDate())
                    .build();
        }

        @Override
        public OpusDTO toDTO(Opus opus) {
            var article = cast(opus);
            return ArticleDTO.builder()
                    .id(article.getId())
                    .title(article.getTitle())
                    .publicationDate(article.getPublicationDate())
                    .periodicalName(article.getPeriodicalName())
                    .build();
        }

        private ArticleDTO cast(OpusDTO opusDTO) {
            return (ArticleDTO) opusDTO;
        }

        private Article cast(Opus opus) {
            return (Article) opus;
        }

    }

    private interface IOpusMapper {
        boolean supports(OpusDTO opusDTO);
        boolean supports(Opus opus);

        Opus toEntity(OpusDTO opusDTO);
        OpusDTO toDTO(Opus opus);
    }

}