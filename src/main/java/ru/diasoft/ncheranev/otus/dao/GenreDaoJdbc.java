package ru.diasoft.ncheranev.otus.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.diasoft.ncheranev.otus.dao.mapper.GenreMapper;
import ru.diasoft.ncheranev.otus.domain.Genre;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * JDBC реализация GenreDao
 *
 * @see GenreDao
 */
@Repository
@RequiredArgsConstructor
public class GenreDaoJdbc implements GenreDao {
    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public int count() {
        return requireNonNull(jdbcOperations.getJdbcOperations()
                .queryForObject("select count(1) from genre", Integer.class));
    }

    @Override
    public long insert(Genre genre) {
        var params = new MapSqlParameterSource();
        params.addValues(Map.of("name", genre.getName()));
        var keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update("insert into genre (name) values (:name)",
                params, keyHolder);
        return requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public Optional<Genre> findById(long id) {
        try {
            return Optional.ofNullable(jdbcOperations.queryForObject(
                    "select * from genre where id = :id", Map.of("id", id), new GenreMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Genre> findByName(String name) {
        try {
            return Optional.ofNullable(jdbcOperations.queryForObject(
                    "select * from genre where name = :name", Map.of("name", name), new GenreMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Genre> getAll() {
        return jdbcOperations.getJdbcOperations().query(
                "select * from genre", new GenreMapper());
    }

    @Override
    public void deleteById(long id) {
        jdbcOperations.update("delete from genre where id = :id", Map.of("id", id));
    }
}
