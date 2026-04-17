package ru.diasoft.ncheranev.otus.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.diasoft.ncheranev.otus.domain.Author;
import ru.diasoft.ncheranev.otus.domain.Book;
import ru.diasoft.ncheranev.otus.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * JDBC RowMapper класса Book
 *
 * @see Book
 */
public class BookMapper implements RowMapper<Book> {

    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Book()
                .setId(rs.getLong("id"))
                .setTitle(rs.getString("title"))
                .setAuthor(new Author().setId(rs.getLong("author_id")))
                .setGenre(new Genre().setId(rs.getLong("genre_id")));
    }
}
