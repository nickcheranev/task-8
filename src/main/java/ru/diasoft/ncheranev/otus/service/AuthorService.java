package ru.diasoft.ncheranev.otus.service;

import ru.diasoft.ncheranev.otus.domain.Author;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с авторами
 */
public interface AuthorService {
    /**
     * Создать запись автора
     *
     * @param fio ФИО
     * @return запись автора
     */
    Author create(String fio);

    /**
     * Получить список всех авторов
     *
     * @return список авторов
     */
    List<Author> getAll();

    /**
     * Найти автора по идентификатору
     *
     * @param id идентификатор автора
     * @return Автор
     */
    Optional<Author> findById(long id);

    /**
     * Найти автора по ФИО
     *
     * @param fio ФИО
     * @return автор
     */
    Optional<Author> findByFio(String fio);

    /**
     * Удалить автора по идентификатору
     *
     * @param id идентификатор автора
     */
    void deleteById(long id);
}
