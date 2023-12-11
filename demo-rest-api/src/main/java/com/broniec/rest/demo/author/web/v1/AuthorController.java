package com.broniec.rest.demo.author.web.v1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/author")
@RequiredArgsConstructor
class AuthorController {

    private final AuthorUpdateCommandHandler authorUpdateCommandHandler;
    private final AuthorRegistrationCommandHandler authorRegistrationCommandHandler;
    private final AuthorQueryHandler authorQueryHandler;

    @PostMapping
    public ResponseEntity<?> createAuthor(@RequestBody AuthorDTO authorDTO) {
        return authorRegistrationCommandHandler.handle(authorDTO)
                .fold(
                        exception -> ResponseEntity.badRequest().body(exception),
                        ResponseEntity::ok
                );
    }

    @PutMapping("/{authorId}")
    public ResponseEntity<?> updateAuthor(@PathVariable Long authorId, @RequestBody AuthorDTO authorDTO) {
        return authorUpdateCommandHandler.handle(authorId, authorDTO)
                .fold(
                        exception -> ResponseEntity.badRequest().body(exception),
                        ResponseEntity::ok
                );
    }

    @GetMapping("/{authorId}")
    public ResponseEntity<?> getAuthorById(@PathVariable Long authorId) {
        return authorQueryHandler.queryAuthorById(authorId)
                .fold(
                        exception -> ResponseEntity.badRequest().body(exception),
                        ResponseEntity::ok
                );
    }

}
