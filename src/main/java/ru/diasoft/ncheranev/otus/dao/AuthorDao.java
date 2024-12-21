package ru.diasoft.ncheranev.otus.dao;

import ru.diasoft.ncheranev.otus.domain.Author;

import java.util.List;
import java.util.Optional;

/**
 * DAO сущности Author
 *
 * @see Author
 */
public interface AuthorDao {
    /**
     * Получить количество авторов в БД
     *
     * @return количество авторов
     */
    int count();

    /**
     * Добавить автора в БД
     *
     * @param author автор
     * @return идентификатор добавленного автора
     */
    long insert(Author author);

    /**
     * Найти автора по идентификатору
     *
     * @param id идентификатор целевого автора
     * @return автор (Optional)
     */
    Optional<Author> findById(long id);

    /**
     * Найти автора по ФИО
     *
     * @param fio ФИО целевого автора
     * @return автор (Optional)
     */
    Optional<Author> findByFio(String fio);

    /**
     * Получить всех авторов
     *
     * @return список авторов
     */
    List<Author> getAll();

    /**
     * Удалить автора по id
     *
     * @param id идентификатор удаляемого автора
     */
    void deleteById(long id);
}
