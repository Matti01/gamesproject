package com.gamesproject.controller;

import com.gamesproject.dao.GamesDao;
import com.gamesproject.dao.PublisherDao;
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
public class PublisherController extends AbstractController implements PublisherApi{

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private static final Logger logger = LoggerFactory.getLogger(PublisherController.class);

    public PublisherController(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return PublisherApi.super.getRequest();
    }

    @Override
    public ResponseEntity<List<Publisher>> publisherGet() {
        PublisherDao publisherDao = new PublisherDao(jdbcTemplate);
        logger.info("Successfully selected all Publishers");
        return getRespond(publisherDao.selectAll());
    }

    @Override
    public ResponseEntity<Publisher> publisherIdGet(Integer id) {
        PublisherDao publisherDao = new PublisherDao(jdbcTemplate);
        Optional<Publisher> publisher = publisherDao.selectById(id);
        logger.info("Successfully selected a Publisher");
        return getRespond(publisher.orElse(null));
    }


    @Override
    public ResponseEntity<Integer> publisherIdDelete(Integer id) {
        PublisherDao publisherDao = new PublisherDao(jdbcTemplate);
        logger.info("Successfully deleted a Publisher");
        return deleteRespond(publisherDao.deleteById(id));
    }

    @Override
    public ResponseEntity<Void> publisherPost(Publisher publisher) {
        PublisherDao publisherDao = new PublisherDao(jdbcTemplate);
        publisherDao.insert(publisher);
        logger.info("Successfully created a Publisher");
        return postRespond();
    }

    @Override

    public ResponseEntity<Integer> publisherPut(Publisher publisher) {
        PublisherDao publisherDao = new PublisherDao(jdbcTemplate);
        logger.info("Successfully updated a Publisher");
        return putRespond(publisherDao.update(publisher));
    }
}
