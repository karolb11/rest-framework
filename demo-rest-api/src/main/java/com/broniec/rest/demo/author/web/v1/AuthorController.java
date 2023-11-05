package com.broniec.rest.demo.author.web.v1;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/author")
@RequiredArgsConstructor
class AuthorController {

    private final AuthorCommandHandler authorCommandHandler;
    private final AuthorQueryHandler authorQueryHandler;

    @PostMapping
    public ResponseEntity<?> createAuthor(@RequestBody AuthorDTO authorDTO) {
        var response = authorCommandHandler.registerAuthor(authorDTO);
        return response.fold(
                res -> ResponseEntity.badRequest().body(res),
                ResponseEntity::ok
                );
    }

    @GetMapping("/{authorId}")
    public ResponseEntity<?> getAuthorById(@PathVariable UUID authorId) {
        return ResponseEntity.of(authorQueryHandler.queryAuthorById(authorId));
    }

}
