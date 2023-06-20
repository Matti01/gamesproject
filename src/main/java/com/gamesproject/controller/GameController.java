package com.gamesproject.controller;

import com.gamesproject.dto.Games;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class GameController extends AbstractController implements GamesApi{

    private final List<Games> gamesList  = new ArrayList<>();
    private final NamedParameterJdbcTemplate jdbcTemplate;
    public GameController(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return GamesApi.super.getRequest();
    }

    @Override
    public ResponseEntity<List<Games>> gamesGet() {
        return getRespond(gamesList);
    }

    @Override
    public ResponseEntity<Void> gamesIdDelete(Integer id) {
        return GamesApi.super.gamesIdDelete(id);
    }

    @Override
    public ResponseEntity<Void> gamesIdPut(Integer id) {
        return GamesApi.super.gamesIdPut(id);
    }

    @Override
    public ResponseEntity<Void> gamesPost(Games games) {
        gamesList.add(games);
        return postRespond();
    }

}
