package com.gamesproject.dao;

import com.gamesproject.dto.Publisher;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import java.util.List;
import java.util.Optional;

public class PublisherDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final static String SELECT_ALL_PUBLISHER = "SELECT * FROM publisher";
    private final static String SELECT_BY_ID = "SELECT * FROM publisher WHERE id = :id";
    private final static String DELETE_BY_ID = "DELETE FROM publisher WHERE id = :id";
    private final static String INSERT_PUBLISHER = "INSERT INTO publisher (id, publisherName) VALUES (:id, :publisherName)";
    private final static String UPDATE_BY_ID = "update publisher set publisherName = :publisherName where id = :id";

    public PublisherDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int insert(Publisher publisher) {
        return jdbcTemplate.update(INSERT_PUBLISHER, new MapSqlParameterSource()
                .addValue("id", publisher.getId())
                .addValue("publisherName", publisher.getPublisherName())
        );
    }

    public List<Publisher> selectAll() {
        return jdbcTemplate.query(SELECT_ALL_PUBLISHER, new MapSqlParameterSource(),
                (rs, rowNum) -> {
                    int id = rs.getInt("id");
                    String publisherName = rs.getString("publisherName");
                    Publisher publisher = new Publisher();
                    publisher.id(id).setPublisherName(publisherName);
                    return publisher;
                }
        );
    }

    public Optional<Publisher> selectById(int id) {
        List<Publisher> publisherList = jdbcTemplate.query(SELECT_BY_ID, new MapSqlParameterSource().addValue("id", id),
                (rs, rowNum) -> {
                    Publisher publisher = new Publisher();
                    publisher.setPublisherName(rs.getString("publisherName"));
                    publisher.setId(rs.getInt("id"));
                    return publisher;
                }
        );
        if (publisherList.size() > 1) throw new IllegalStateException("There must not be more than one entry" +
                "in the DB because studentID column is a primary key.");
        else if (publisherList.isEmpty()) return Optional.empty();
        else return Optional.of(publisherList.get(0));
    }

    public int deleteById(int id) {
        return  jdbcTemplate.update(DELETE_BY_ID, new MapSqlParameterSource().addValue("id", id));
    }

public int update(Publisher publisher) {
        return  jdbcTemplate.update(UPDATE_BY_ID, new MapSqlParameterSource()
                .addValue("id", publisher.getId())
                .addValue("publisherName",publisher.getPublisherName()));
    }
}
