package com.gamesproject.dao;

import com.gamesproject.dto.Publisher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PublisherDaoTest {

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Test
    void insert() {
        //arrange
        PublisherDao publisherDao = new PublisherDao(this.namedParameterJdbcTemplate);
        //act
        Publisher publisher = new Publisher();
        publisher.setId(1);
        publisher.setPublisherName("test");
        publisherDao.insert(publisher);
        //assert
        ArgumentCaptor<MapSqlParameterSource> argumentCaptor = ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(this.namedParameterJdbcTemplate, times(1)).update(
                eq("INSERT INTO publisher (id, publisherName) VALUES (:id, :publisherName)"),
                argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getValue("id"));
    }

    @Test
    void selectAll() {
        //arrange
        PublisherDao publisherDao = new PublisherDao(this.namedParameterJdbcTemplate);
        //act
        publisherDao.selectAll();
        //assert
        ArgumentCaptor<MapSqlParameterSource> argumentCaptor = ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(this.namedParameterJdbcTemplate, times(1)).query(
                eq("SELECT * FROM publisher"),
                argumentCaptor.capture(),
                ArgumentMatchers.<RowMapper<Publisher>>any());
        assertThat(argumentCaptor.getValue().getValue("id"));
    }

    @Test
    void selectById() {
        //arrange
        PublisherDao publisherDao = new PublisherDao(this.namedParameterJdbcTemplate);
        //act
        publisherDao.selectById(1);
        //assert
        ArgumentCaptor<MapSqlParameterSource> argumentCaptor = ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(this.namedParameterJdbcTemplate, times(1)).query(
                eq("SELECT * FROM publisher WHERE id = :id"),
                argumentCaptor.capture(),
                ArgumentMatchers.<RowMapper<Publisher>>any());
        assertThat(argumentCaptor.getValue().getValue("id"));
    }


    @Test
    void update() {
        //arrange
        PublisherDao publisherDao = new PublisherDao(this.namedParameterJdbcTemplate);
        //act
        Publisher publisher = new Publisher();
        publisher.setId(1);
        publisher.setPublisherName("test");
        publisherDao.update(publisher);
        //assert
        ArgumentCaptor<MapSqlParameterSource> argumentCaptor = ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(this.namedParameterJdbcTemplate, times(1)).update(
                eq("update publisher set publisherName = :publisherName where id = :id"),
                argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getValue("id"));
    }

    @Test
    void delete() {
        //arrange
        PublisherDao publisherDao = new PublisherDao(this.namedParameterJdbcTemplate);
        //act
        publisherDao.deleteById(1);
        //assert
        ArgumentCaptor<MapSqlParameterSource> argumentCaptor = ArgumentCaptor.forClass(MapSqlParameterSource.class);
        verify(this.namedParameterJdbcTemplate, times(1)).update(
                eq("DELETE FROM publisher WHERE id = :id"),
                argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getValue("id"));
    }
}
