package com.broniec.rest.demo.web.v1.opus;

import org.springframework.stereotype.Component;

import com.broniec.rest.demo.domain.Book;
import com.broniec.rest.demo.domain.Opus;

@Component
class BookMapper implements IOpusMapper {

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
