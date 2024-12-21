package ru.diasoft.ncheranev.otus.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.diasoft.ncheranev.otus.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * JDBC RowMapper класса Author
 *
 * @see Author
 */
public class AuthorMapper implements RowMapper<Author> {

    @Override
    public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Author()
                .setId(rs.getLong("id"))
                .setFio(rs.getString("fio"));
    }
}