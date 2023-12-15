package com.broniec.rest.demo.author.web.v1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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

    private final AuthorUpdateCommandHandler authorUpdateCommandHandler;
    private final AuthorRegistrationCommandHandler authorRegistrationCommandHandler;
    private final OpusRegistrationCommandHandler opusRegistrationCommandHandler;
    private final AuthorQueryHandler authorQueryHandler;

    @GetMapping("/{authorId}")
    public ResponseEntity<?> getAuthorById(@PathVariable Long authorId) {
        return ResponseEntity.of(authorQueryHandler.queryAuthorById(authorId));
    }

    @PostMapping
    public ResponseEntity<?> createAuthor(@RequestBody AuthorDTO authorDTO) {
        try {
            var response = authorRegistrationCommandHandler.handle(authorDTO);
            return ResponseEntity.ok(response);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest()
                    .body(e.getConstraintViolations());
        }
    }

    @PatchMapping("/{authorId}")
    public ResponseEntity<?> updateAuthor(@PathVariable Long authorId, @RequestBody AuthorDTO authorDTO) {
        try {
            var response = authorUpdateCommandHandler.handle(authorId, authorDTO);
            return ResponseEntity.ok(response);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest()
                    .body(e.getConstraintViolations());
        }
    }

    @PostMapping("/{authorId}/opus")
    public ResponseEntity<?> createOpus(@PathVariable Long authorId, @RequestBody OpusDTO opusDTO) {
        try {
            var response = opusRegistrationCommandHandler.handle(authorId, opusDTO);
            return ResponseEntity.ok(response);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest()
                    .body(e.getConstraintViolations());
        }
    }

}
