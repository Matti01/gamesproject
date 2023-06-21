package com.gamesproject.controller;

import com.gamesproject.dao.GamesDao;
import com.gamesproject.dao.GamesDao;
import com.gamesproject.dto.Games;
import com.gamesproject.dto.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(PublisherController.class);

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
        logger.info("Successfully selected all Games");
        return getRespond(gamesDao.selectAll());
    }

    @Override
    public ResponseEntity<Games> gamesIdGet(Integer id) {
        GamesDao gamesDao = new GamesDao(jdbcTemplate);
        Optional<Games> games = gamesDao.selectById(id);
        logger.info("Successfully selected a Game");
        return getRespond(games.orElse(null));
    }


    @Override
    public ResponseEntity<Integer> gamesIdDelete(Integer id) {
        GamesDao gamesDao = new GamesDao(jdbcTemplate);
        logger.info("Successfully deleted a Game");
        return deleteRespond(gamesDao.deleteById(id));
    }

    @Override
    public ResponseEntity<Void> gamesPost(Games game) {
        GamesDao gamesDao = new GamesDao(jdbcTemplate);
        gamesDao.insert(game);
        logger.info("Successfully created a Game");
        return postRespond();
    }

    @Override
    public ResponseEntity<Integer> gamesPut(Games game) {
        GamesDao gamesDao = new GamesDao(jdbcTemplate);
        logger.info("Successfully updated a Game");
        return putRespond(gamesDao.update(game));
    }
}
