package ru.diasoft.ncheranev.otus.dao;

import ru.diasoft.ncheranev.otus.domain.Genre;

import java.util.List;
import java.util.Optional;

/**
 * DAO сущности Genre (Жанр)
 *
 * @see Genre
 */
public interface GenreDao {
    /**
     * Получить количество книг в БД
     *
     * @return количество
     */
    int count();

    /**
     * Добавить жанр
     *
     * @param genre добавляемый жанр
     * @return идентификатор добавленного автора
     */
    long insert(Genre genre);

    /**
     * Найти жанр по идентификатору
     *
     * @param id идентификатор жанра
     * @return Жанр
     */
    Optional<Genre> findById(long id);

    /**
     * Найти жанр по наименованию
     *
     * @param name наименование жанра
     * @return Жанр
     */
    Optional<Genre> findByName(String name);

    /**
     * Получить все жанры из БД
     *
     * @return список жаров
     */
    List<Genre> getAll();

    /**
     * Удалить жанр по идентификатору
     *
     * @param id идентификатор удаляемого жанра
     */
    void deleteById(long id);
}
