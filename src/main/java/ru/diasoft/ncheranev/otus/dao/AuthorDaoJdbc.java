package ru.diasoft.ncheranev.otus.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.diasoft.ncheranev.otus.dao.mapper.AuthorMapper;
import ru.diasoft.ncheranev.otus.domain.Author;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * JDBC реализация AuthorDao
 *
 * @see AuthorDao
 */
@Repository
@RequiredArgsConstructor
public class AuthorDaoJdbc implements AuthorDao {
    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public int count() {
        return requireNonNull(jdbcOperations.getJdbcOperations()
                .queryForObject("select count(1) from author", Integer.class));
    }

    @Override
    public long insert(Author author) {
        var params = new MapSqlParameterSource();
        params.addValues(Map.of("fio", author.getFio()));
        var keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update("insert into author (fio) values (:fio)",
                params, keyHolder);
        return requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public Optional<Author> findById(long id) {
        try {
            return Optional.ofNullable(jdbcOperations.queryForObject(
                    "select * from author where id = :id", Map.of("id", id), new AuthorMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Author> findByFio(String fio) {
        try {
            return Optional.ofNullable(jdbcOperations.queryForObject(
                    "select * from author where fio = :fio", Map.of("fio", fio), new AuthorMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Author> getAll() {
        return jdbcOperations.getJdbcOperations().query(
                "select * from author", new AuthorMapper());
    }

    @Override
    public void deleteById(long id) {
        jdbcOperations.update("delete from author where id = :id", Map.of("id", id));
    }
}
