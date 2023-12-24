package com.broniec.rest.demo.web.v1.opus;

import org.springframework.stereotype.Component;

import com.broniec.rest.demo.domain.Article;
import com.broniec.rest.demo.domain.Opus;

@Component
class ArticleMapper implements IOpusMapper {

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
