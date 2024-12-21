package ru.diasoft.ncheranev.otus.service;

import ru.diasoft.ncheranev.otus.domain.Genre;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с жанрами
 */
public interface GenreService {
    /**
     * Создать запись жанра
     *
     * @param name наименование жанра
     * @return жанр
     */
    Genre create(String name);

    /**
     * Получить все жанры
     *
     * @return список жанров
     */
    List<Genre> getAll();

    /**
     * Найти жанр по идентификатору
     *
     * @param id идентификатор жанра
     * @return жанр
     */
    Optional<Genre> findById(long id);

    /**
     * Найти жанр по наименованию
     *
     * @param name наименование жанра
     * @return жанр
     */
    Optional<Genre> findByName(String name);

    /**
     * Удалить жанр по идентификатору
     *
     * @param id идентификатор жанра
     */
    void deleteById(long id);
}