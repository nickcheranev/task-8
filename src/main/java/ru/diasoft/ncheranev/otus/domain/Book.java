package ru.diasoft.ncheranev.otus.domain;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Книга
 */
@Data
@Accessors(chain = true)
public class Book {
    /**
     * Идентификатор
     */
    private long id;
    /**
     * Название
     */
    private String title;
    /**
     * Автор
     */
    private Author author;
    /**
     * Жанр
     */
    private Genre genre;

    public String toString() {
        return "[" + id + "]" + title + " " + author + " " + genre;
    }
}
