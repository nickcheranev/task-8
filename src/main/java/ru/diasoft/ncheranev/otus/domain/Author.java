package ru.diasoft.ncheranev.otus.domain;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Автор книги
 */
@Data
@Accessors(chain = true)
public class Author {
    /**
     * Идентификатор
     */
    private long id;
    /**
     * Фамилия Имя (Отчество) автора
     */
    private String fio;

    public String toString() {
        return "[" + id + "]" + fio;
    }
}
