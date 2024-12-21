package ru.diasoft.ncheranev.otus.domain;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Жанр книги
 */
@Data
@Accessors(chain = true)
public class Genre {
    /**
     * Идентификатор
     */
    private long id;
    /**
     * Наименование жанра
     */
    private String name;

    public String toString() {
        return "[" + id + "]" + name;
    }
}
