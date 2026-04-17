package ru.diasoft.ncheranev.otus.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.diasoft.ncheranev.otus.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * JDBC RowMapper класса Genre
 *
 * @see Genre
 */
public class GenreMapper implements RowMapper<Genre> {

    @Override
    public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Genre()
                .setId(rs.getLong("id"))
                .setName(rs.getString("name"));
    }
}
