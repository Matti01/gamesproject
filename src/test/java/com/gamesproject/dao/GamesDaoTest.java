package com.gamesproject.dao;

import com.gamesproject.dto.Games;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
@ExtendWith(MockitoExtension.class)
public class GamesDaoTest {
    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Test
    void insert() {
        //arrange
        GamesDao gamesDao = new GamesDao(this.namedParameterJdbcTemplate);
        //act
        Games games = new Games();
        games.setId(1);
        games.setGameName("test");
        games.setPublisherId(1);
        games.setReleaseDate(LocalDate.parse("2006-04-25"));
        games.setPlatform("Nintendo");
        gamesDao.insert(games);
        //assert
        ArgumentCaptor<MapSqlParameterSource> argumentCaptor = ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(this.namedParameterJdbcTemplate, times(1)).update(
                eq("INSERT INTO games (id, gameName, publisher_id, releaseDate, platform) VALUES (:id, :gameName, :publisher_id, :releaseDate, :platform)"),
                argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getValue("id"));
    }

    @Test
    void selectAll() {
        //arrange
        GamesDao gamesDao = new GamesDao(this.namedParameterJdbcTemplate);
        //act
        gamesDao.selectAll();
        //assert
        ArgumentCaptor<MapSqlParameterSource> argumentCaptor = ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(this.namedParameterJdbcTemplate, times(1)).query(
                eq("SELECT * FROM games"),
                argumentCaptor.capture(),
                ArgumentMatchers.<RowMapper<Games>>any());
    }

    @Test
    void selectById() {
        //arrange
        GamesDao gamesDao = new GamesDao(this.namedParameterJdbcTemplate);
        //act
        gamesDao.selectById(1);
        //assert
        ArgumentCaptor<MapSqlParameterSource> argumentCaptor = ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(this.namedParameterJdbcTemplate, times(1)).query(
                eq("SELECT * FROM games WHERE id = :id"),
                argumentCaptor.capture(),
                ArgumentMatchers.<RowMapper<Games>>any());
        assertThat(argumentCaptor.getValue().getValue("id")).isEqualTo(1);
    }


    @Test
    void update() {
        //arrange
        GamesDao gamesDao = new GamesDao(this.namedParameterJdbcTemplate);
        //act
        Games games = new Games();
        games.setId(1);
        games.setGameName("test");
        games.setPublisherId(1);
        games.setReleaseDate(LocalDate.parse("2006-04-25"));
        games.setPlatform("Nintendo");
        gamesDao.insert(games);
        //assert
        ArgumentCaptor<MapSqlParameterSource> argumentCaptor = ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(this.namedParameterJdbcTemplate, times(1)).update(
                eq("update games set gameName = :gameName, publisher_id = :publisher_id, releaseDate = :releaseDate, platform = :platform where id = :id"),
                argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getValue("id"));
    }

    @Test
    void delete() {
        //arrange
        GamesDao gamesDao = new GamesDao(this.namedParameterJdbcTemplate);
        //act
        gamesDao.deleteById(1);
        //assert
        ArgumentCaptor<MapSqlParameterSource> argumentCaptor = ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(this.namedParameterJdbcTemplate, times(1)).update(
                eq("DELETE FROM games WHERE id = :id"),
                argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getValue("id"));
    }
}
