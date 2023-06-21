package com.gamesproject.configuration;

import com.gamesproject.dao.GamesDao;
import com.gamesproject.dao.PublisherDao;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

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

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(request -> request
                        .requestMatchers(HttpMethod.POST, "/games", "/publisher")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/games/{id}", "/publisher/{id}")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/games", "/publisher")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/games", "/publisher", "/games/{id}", "/publisher/{id}")
                        .permitAll()
                        .anyRequest()
                        .permitAll()
                ).httpBasic(Customizer.withDefaults());

        return http.csrf().disable().build();
    }

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        var uds = new JdbcUserDetailsManager(dataSource);
        if (!uds.userExists("admin")) {
            uds.createUser(User.withUsername("admin").password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("admin")).roles("ADMIN").build());
        }
        return uds;
    }

}
