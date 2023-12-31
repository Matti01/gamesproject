package com.gamesproject.controller;

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
        if (publisherDao.selectById(id).isEmpty()){
            logger.warn("Selected publisher does not exist");
            return errorResponse();
        }
        else {
            logger.info("Successfully selected a Publisher");
            return getRespond(publisher.orElse(null));
        }

    }


    @Override
    public ResponseEntity<Integer> publisherIdDelete(Integer id) {
            PublisherDao publisherDao = new PublisherDao(jdbcTemplate);
            logger.info("Successfully deleted a Publisher");
            if (publisherDao.selectById(id).isEmpty()){
                logger.warn("Selected publisher does not exist");
                return errorResponse();
            }
            else {
                try {
                    return deleteRespond(publisherDao.deleteById(id));
                }
                catch (Exception ex){
                    logger.warn("Selected publisher cannot be deleted because it is referenced in a game");
                    return errorResponse();
                }
            }
    }

    @Override
    public ResponseEntity<Void> publisherPost(Publisher publisher) {
        try {
            PublisherDao publisherDao = new PublisherDao(jdbcTemplate);
            publisherDao.insert(publisher);
                logger.info("Successfully created a Publisher");
                return postRespond();
        }
        catch (Exception ex){
            logger.warn(ex.getMessage());
            return errorResponse();
        }
    }

    @Override

    public ResponseEntity<Integer> publisherPut(Publisher publisher) {
        try {
            PublisherDao publisherDao = new PublisherDao(jdbcTemplate);
            if (publisherDao.selectById(publisher.getId()).isEmpty()){
                logger.warn("Selected publisher does not exist");
                return errorResponse();
            }
            else {
                logger.info("Successfully updated a Publisher");
                return putRespond(publisherDao.update(publisher));
            }
        }
        catch (Exception ex){
            logger.warn(ex.getMessage());
            return errorResponse();
        }
    }
}
