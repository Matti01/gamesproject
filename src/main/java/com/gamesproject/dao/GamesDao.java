package com.gamesproject.dao;

import com.gamesproject.dto.Games;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;
import java.util.Optional;

public class GamesDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    public GamesDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insert(Games games){

    }

    public Optional<Games> selectAll(){
        String sql = "SELECT * FROM games";
        return null;
    }

    public void deleteById(int id){

    }

    public void update(int id){

    }
}
