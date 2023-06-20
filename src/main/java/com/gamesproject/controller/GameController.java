package com.gamesproject.controller;

import com.gamesproject.dao.GamesDao;
import com.gamesproject.dao.GamesDao;
import com.gamesproject.dto.Games;
import com.gamesproject.dto.Publisher;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class GameController extends AbstractController implements GamesApi{
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
        GamesDao gamesDao = new GamesDao(jdbcTemplate);
        return getRespond(gamesDao.selectAll());
    }

    @Override
    public ResponseEntity<Games> gamesIdGet(Integer id) {
        GamesDao gamesDao = new GamesDao(jdbcTemplate);
        Optional<Games> games = gamesDao.selectById(id);
        return getRespond(games.orElse(null));
    }


    @Override
    public ResponseEntity<Integer> gamesIdDelete(Integer id) {
        GamesDao gamesDao = new GamesDao(jdbcTemplate);
        return deleteRespond(gamesDao.deleteById(id));
    }

    @Override
    public ResponseEntity<Void> gamesPost(Games game) {
        GamesDao gamesDao = new GamesDao(jdbcTemplate);
        gamesDao.insert(game);
        return postRespond();
    }

    @Override
    public ResponseEntity<Integer> gamesPut(Games game) {
        GamesDao gamesDao = new GamesDao(jdbcTemplate);
        return putRespond(gamesDao.update(game));
    }
}
