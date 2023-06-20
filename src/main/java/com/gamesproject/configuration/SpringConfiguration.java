package com.gamesproject.configuration;

import com.gamesproject.dao.GamesDao;
import com.gamesproject.dao.PublisherDao;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
@EnableConfigurationProperties
public class SpringConfiguration {
    @Bean
    GamesDao gamesDao(NamedParameterJdbcTemplate jdbcTemplate){
        return new GamesDao(jdbcTemplate);
    }

    @Bean
    PublisherDao publisherDao(NamedParameterJdbcTemplate jdbcTemplate){
        return new PublisherDao(jdbcTemplate);
    }

}
