package ru.diasoft.ncheranev.otus.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.diasoft.ncheranev.otus.domain.Author;
import ru.diasoft.ncheranev.otus.domain.Book;
import ru.diasoft.ncheranev.otus.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("Dao для работы с книгами")
@JdbcTest
@Import(BookDaoJdbc.class)
class BookDaoJdbcTest {
    private static final int EXPECTED_BOOKS_COUNT = 1;
    private static final long EXISTING_BOOK_ID = 10;
    private static final long NOT_EXISTING_BOOK_ID = 20;
    private static final String EXISTING_BOOK_TITLE = "Чистый код";
    private static final String NOT_EXISTING_BOOK_TITLE = "Отсутствующая книга";
    private static final long EXISTING_AUTHOR_ID = 10;
    private static final long EXISTING_GENRE_ID = 10;
    private static final Book EXISTED_BOOK = new Book()
            .setId(EXISTING_BOOK_ID)
            .setTitle(EXISTING_BOOK_TITLE)
            .setGenre(new Genre().setId(EXISTING_GENRE_ID))
            .setAuthor(new Author().setId(EXISTING_AUTHOR_ID));

    @Autowired
    private BookDaoJdbc sut;

    @Test
    @DisplayName("должен возвращать ожидаемый список книг")
    void shouldReturnExpectedBookList() {
        var actualBookList = sut.getAll();

        assertThat(actualBookList)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(EXISTED_BOOK);
    }

    @Test
    @DisplayName("должен добавлять книгу в БД")
    void shouldInsertBook() {
        var expectedBook = new Book()
                .setTitle("Настоящий программист")
                .setAuthor(new Author().setId(EXISTING_AUTHOR_ID))
                .setGenre(new Genre().setId(EXISTING_GENRE_ID));

        var id = sut.insert(expectedBook);
        var optActualBook = sut.findById(id);
        expectedBook.setId(id);

        assertThat(optActualBook.orElseThrow()).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @Test
    @DisplayName("должен возвращать ожидаемую книгу по id при ее наличии")
    void shouldReturnExpectedBookById() {
        var actualBook = sut.findById(EXISTED_BOOK.getId());

        assertThat(actualBook.orElseThrow()).usingRecursiveComparison().isEqualTo(EXISTED_BOOK);
    }

    @Test
    @DisplayName("должен возвращать Optional.empty() в случае ее отсутствия при поиске по id")
    void shouldReturnOptionalEmptyWhenBookNotFoundById() {
        var actualBook = sut.findById(NOT_EXISTING_BOOK_ID);

        assertThat(actualBook).isEmpty();
    }

    @Test
    @DisplayName("должен возвращать ожидаемую книгу по title при ее наличии")
    void shouldReturnExpectedBookByTitle() {
        var actualBook = sut.findByTitle(EXISTED_BOOK.getTitle());

        assertThat(actualBook.orElseThrow()).usingRecursiveComparison().isEqualTo(EXISTED_BOOK);
    }

    @Test
    @DisplayName("должен возвращать Optional.empty() в случае ее отсутствия при поиске по title")
    void shouldReturnOptionalEmptyWhenBookNotFoundByTitle() {
        var actualBook = sut.findByTitle(NOT_EXISTING_BOOK_TITLE);

        assertThat(actualBook).isEmpty();
    }

    @Test
    @DisplayName("должен удалять заданную книгу по её id")
    void shouldCorrectDeletePersonById() {
        assertThatCode(() -> sut.findById(EXISTING_BOOK_ID))
                .doesNotThrowAnyException();

        sut.deleteById(EXISTING_BOOK_ID);

        assertThat(sut.findById(EXISTING_BOOK_ID)).isEmpty();
    }

    @Test
    @DisplayName("должен возвращать ожидаемое количество книг в БД")
    void shouldReturnExpectedBookCount() {
        var actualBookCount = sut.count();

        assertThat(actualBookCount).isEqualTo(EXPECTED_BOOKS_COUNT);
    }

    @Test
    @DisplayName("должен возвращать все книги по id автора (author_id)")
    void shouldReturnAllBooksByAuthorId() {
        var actualBooks = sut.getAllByAuthorId(EXISTING_AUTHOR_ID);

        assertThat(actualBooks).isEqualTo(List.of(EXISTED_BOOK));
    }

    @Test
    @DisplayName("должен возвращать все книги по id жанра (genre_id)")
    void getAllByGenreId() {
        var actualBooks = sut.getAllByGenreId(EXISTING_GENRE_ID);

        assertThat(actualBooks).isEqualTo(List.of(EXISTED_BOOK));
    }

    @Test
    @DisplayName("должен возвращать True при наличии книги заданного автора")
    void shouldReturnTrueWhenExistsByTitleAndAuthorId() {
        assertThat(sut.existsByTitleAndAuthorId(EXISTING_BOOK_TITLE, EXISTING_AUTHOR_ID)).isTrue();
    }

    @Test
    @DisplayName("должен возвращать False при отсутствии книги заданного автора")
    void shouldReturnFalseWhenNotExistsByTitleAndAuthorId() {
        assertThat(sut.existsByTitleAndAuthorId(NOT_EXISTING_BOOK_TITLE, EXISTING_AUTHOR_ID)).isFalse();
    }
}