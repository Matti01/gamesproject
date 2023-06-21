package com.gamesproject.controller;

import com.gamesproject.dao.GamesDao;
import com.gamesproject.dao.PublisherDao;
import com.gamesproject.dto.Games;
import com.gamesproject.dto.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

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
        if (gamesDao.selectById(id).isEmpty()){
            logger.warn("Selected ID does not exist");
            return errorResponse();
        }
        else {
            logger.info("Successfully selected a Game");
            return getRespond(games.orElse(null));
        }
    }


    @Override
    public ResponseEntity<Integer> gamesIdDelete(Integer id) {
        GamesDao gamesDao = new GamesDao(jdbcTemplate);
        if (gamesDao.selectById(id).isEmpty()){
            logger.warn("Selected ID does not exist");
            return errorResponse();
        }
        else {
            logger.info("Successfully deleted a Game");
            return deleteRespond(gamesDao.deleteById(id));
        }
    }

    @Override
    public ResponseEntity<Void> gamesPost(Games game) {
        try {
            GamesDao gamesDao = new GamesDao(jdbcTemplate);
            PublisherDao publisherDao = new PublisherDao(jdbcTemplate);
            if (publisherDao.selectById(game.getPublisherId()).isEmpty()){
                logger.warn("Selected publisher ID does not exist");
                return errorResponse();
            }
            gamesDao.insert(game);
            logger.info("Successfully created a Game");
            return postRespond();
        }
        catch (Exception ex){
            logger.warn(ex.getMessage());
            return errorResponse();
        }
    }

    @Override
    public ResponseEntity<Integer> gamesPut(Games game) {
        try {
            GamesDao gamesDao = new GamesDao(jdbcTemplate);
            PublisherDao publisherDao = new PublisherDao(jdbcTemplate);
            if (publisherDao.selectById(game.getPublisherId()).isEmpty()){
                logger.warn("Selected publisher does not exist");
                return errorResponse();
            } else if (gamesDao.selectById(game.getId()).isEmpty()) {
                logger.warn("Selected game does not exist");
                return errorResponse();
            } else {
                logger.info("Successfully updated a Game");
                return putRespond(gamesDao.update(game));
            }
        }
        catch (Exception ex){
            logger.warn(ex.getMessage());
            return errorResponse();
        }
    }
}
