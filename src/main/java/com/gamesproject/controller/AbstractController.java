package com.gamesproject.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public abstract class AbstractController {

    protected static <T> ResponseEntity<T> getAllGames(T result) {
        return ResponseEntity.ok(result);
    }

    protected static <T> ResponseEntity<T> addGame() {
        return ResponseEntity.ok().build();
    }

    protected static <T> ResponseEntity<T> deleteGame() {
        return ResponseEntity.ok().build();
    }

    protected static <T> ResponseEntity<T> updateGame() {
        return ResponseEntity.ok().build();
    }

    protected static <T> ResponseEntity<T> getAllPublishers(T result) {
        return ResponseEntity.ok(result);
    }

    protected static <T> ResponseEntity<T> addPublisher() {
        return ResponseEntity.ok().build();
    }

    protected static <T> ResponseEntity<T> deletePublisher() {
        return ResponseEntity.ok().build();
    }

    protected static <T> ResponseEntity<T> updatePublisher() {
        return ResponseEntity.ok().build();
    }
}
