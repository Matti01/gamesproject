package com.gamesproject.dao;

import com.gamesproject.dto.Games;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class GamesDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final static String SELECT_ALL_GAMES = "SELECT * FROM games";
    private final static String SELECT_BY_ID = "SELECT * FROM games WHERE id = :id";
    private final static String DELETE_BY_ID = "DELETE FROM games WHERE id = :id";
    private final static String INSERT_GAME = "INSERT INTO games (id, gameName, publisher_id, releaseDate, platform) VALUES (:id, :gameName, :publisher_id, :releaseDate, :platform)";
    private final static String UPDATE_BY_ID = "update games set gameName = :gameName, publisher_id = :publisher_id, releaseDate = :releaseDate, platform = :platform where id = :id";

    public GamesDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int insert(Games game) {
        return jdbcTemplate.update(INSERT_GAME, new MapSqlParameterSource()
                .addValue("id", game.getId())
                .addValue("gameName", game.getGameName())
                .addValue("publisher_id", game.getPublisherId())
                .addValue("releaseDate", game.getReleaseDate())
                .addValue("platform", game.getPlatform())
        );
    }

    public List<Games> selectAll() {
        return jdbcTemplate.query(SELECT_ALL_GAMES, new MapSqlParameterSource(),
                (rs, rowNum) -> {
                    int id = rs.getInt("id");
                    String gameName = rs.getString("gameName");
                    int publisherId = rs.getInt("publisher_id");
                    LocalDate releaseDate = rs.getDate("releaseDate").toLocalDate();
                    String platform = rs.getString("platform");
                    Games game = new Games();
                    game.id(id).setGameName(gameName);
                    game.setPublisherId(publisherId);
                    game.setReleaseDate(releaseDate);
                    game.setPlatform(platform);
                    return game;
                }
        );
    }

    public Optional<Games> selectById(int id) {
        List<Games> gamesList = jdbcTemplate.query(SELECT_BY_ID, new MapSqlParameterSource().addValue("id", id),
                (rs, rowNum) -> {
                    Games games = new Games();
                    games.setGameName(rs.getString("gameName"));
                    games.setId(rs.getInt("id"));
                    games.setPlatform(rs.getString("platform"));
                    games.setPublisherId(rs.getInt("publisher_id"));
                    games.setReleaseDate(rs.getDate("releaseDate").toLocalDate());
                    return games;
                }
        );
        if (gamesList.size() > 1) throw new IllegalStateException("There must not be more than one entry" +
                "in the DB because studentID column is a primary key.");
        else if (gamesList.isEmpty()) return Optional.empty();
        else return Optional.of(gamesList.get(0));
    }

    public int deleteById(int id) {
        return jdbcTemplate.update(DELETE_BY_ID, new MapSqlParameterSource().addValue("id", id));
    }

    public int update(Games game) {
        return jdbcTemplate.update(UPDATE_BY_ID, new MapSqlParameterSource()
                .addValue("id", game.getId())
                .addValue("gameName", game.getGameName())
                .addValue("publisher_id", game.getPublisherId())
                .addValue("releaseDate", game.getReleaseDate())
                .addValue("platform", game.getPlatform())
        );
    }
}
