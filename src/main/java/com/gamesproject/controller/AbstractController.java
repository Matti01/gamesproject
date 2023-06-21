package com.gamesproject.controller;

import com.gamesproject.dto.Publisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public abstract class AbstractController {

    protected static <T> ResponseEntity<T> getRespond(T result) {
        return ResponseEntity.ok(result);
    }

    protected static <T> ResponseEntity<T> postRespond() {
        return ResponseEntity.ok().build();
    }

    protected static <T> ResponseEntity<T> deleteRespond(T result) {
        return ResponseEntity.ok().build();
    }

    protected static <T> ResponseEntity<T> putRespond(T result) {
        return ResponseEntity.ok().build();
    }

    protected static <T> ResponseEntity<T> errorResponse(){ return ResponseEntity.internalServerError().build();}
}
