package ru.diasoft.ncheranev.otus.dao;

import ru.diasoft.ncheranev.otus.domain.Book;

import java.util.List;
import java.util.Optional;

/**
 * DAO сущности Book
 *
 * @see Book
 */
public interface BookDao {
    /**
     * Количество книг в БД
     *
     * @return количество книг
     */
    int count();

    /**
     * Добавить книгу в БД
     *
     * @param book добавляемая книга
     * @return идентификатор добавленной книги
     */
    long insert(Book book);

    /**
     * Найти книгу по идентификатору
     *
     * @param id идентификатор искомой книги
     * @return книга (Optional)
     */
    Optional<Book> findById(long id);

    /**
     * Найти книгу по название
     *
     * @param title название искомой книги
     * @return книга (Optional)
     */
    Optional<Book> findByTitle(String title);

    /**
     * Получить все книги
     *
     * @return список книг
     */
    List<Book> getAll();

    /**
     * Удалить книгу по идентификатору
     *
     * @param id идентификатор удаляемой книги
     */
    void deleteById(long id);

    /**
     * Получить список книг автора по его идентификатору
     *
     * @param authorId идентификатор автора
     * @return список книг
     */
    List<Book> getAllByAuthorId(long authorId);

    /**
     * Получить список книг по идентификатору жанра
     *
     * @param genreId идентификатор жанра
     * @return список книг
     */
    List<Book> getAllByGenreId(long genreId);

    /**
     * Проверить наличие книги по названию и идентификатору автора
     *
     * @param title    название книги
     * @param authorId идентификатор автора
     * @return результат проверки
     */
    boolean existsByTitleAndAuthorId(String title, Long authorId);
}
