package com.gamesproject.controller;

import com.gamesproject.dto.Games;
import com.gamesproject.dto.Publisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class MainController extends AbstractController implements GamesApi, PublisherApi{

    private final List<Games> gamesList  = new ArrayList<>();
    private final List<Publisher> publisherList = new ArrayList<>();

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return GamesApi.super.getRequest();
    }

    @Override
    public ResponseEntity<List<Games>> gamesGet() {
        return getAllGames(gamesList);
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
        return addGame();
    }

    @Override
    public ResponseEntity<List<Publisher>> publisherGet() {
        return getAllPublishers(publisherList);
    }

    @Override
    public ResponseEntity<Void> publisherIdDelete(Integer id) {
        return PublisherApi.super.publisherIdDelete(id);
    }

    @Override
    public ResponseEntity<Void> publisherIdPut(Integer id) {
        return PublisherApi.super.publisherIdPut(id);
    }

    @Override
    public ResponseEntity<Void> publisherPost(Publisher publisher) {
        publisherList.add(publisher);
        return addPublisher();
    }
}
