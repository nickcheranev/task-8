package ru.diasoft.ncheranev.otus.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.diasoft.ncheranev.otus.dao.mapper.BookMapper;
import ru.diasoft.ncheranev.otus.domain.Book;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNullElse;

/**
 * JDBC реализация BookDao
 *
 * @see BookDao
 */
@Repository
@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao {
    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public int count() {
        return requireNonNull(jdbcOperations.getJdbcOperations()
                .queryForObject("select count(1) from book", Integer.class));
    }

    @Override
    public long insert(Book book) {
        var params = new MapSqlParameterSource();
        params.addValues(Map.of(
                "title", book.getTitle(),
                "authorId", book.getAuthor().getId(),
                "genreId", book.getGenre().getId()
        ));
        var keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update("insert into book (title, author_id, genre_id) values (:title, :authorId, :genreId)",
                params, keyHolder);
        return requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public Optional<Book> findById(long id) {
        try {
            return Optional.ofNullable(jdbcOperations.queryForObject(
                    "select * from book where id = :id", Map.of("id", id), new BookMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Book> findByTitle(String title) {
        try {
            return Optional.ofNullable(jdbcOperations.queryForObject(
                    "select * from book where title = :title", Map.of("title", title), new BookMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Book> getAll() {
        return jdbcOperations.getJdbcOperations().query(
                "select * from book", new BookMapper());
    }

    @Override
    public void deleteById(long id) {
        jdbcOperations.update("delete from book where id = :id", Map.of("id", id));
    }

    @Override
    public List<Book> getAllByAuthorId(long authorId) {
        return jdbcOperations.query(
                "select * from book where author_id = :authorId", Map.of("authorId", authorId), new BookMapper());
    }

    @Override
    public List<Book> getAllByGenreId(long genreId) {
        return jdbcOperations.query(
                "select * from book where genre_id = :genreId", Map.of("genreId", genreId), new BookMapper());
    }

    @Override
    public boolean existsByTitleAndAuthorId(String title, Long authorId) {
        return requireNonNullElse(jdbcOperations.queryForObject(
                "select count(1) from book where title = :title and author_id = :authorId",
                Map.of("title", title, "authorId", authorId), Integer.class), 0) > 0;
    }
}
