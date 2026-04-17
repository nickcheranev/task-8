package ru.diasoft.ncheranev.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.diasoft.ncheranev.otus.domain.Author;
import ru.diasoft.ncheranev.otus.domain.Book;
import ru.diasoft.ncheranev.otus.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@SpringBootTest
@DisplayName("Сервис для работы с книгами")
class BookServiceImplTest {
    private static final long EXISTING_BOOK_ID = 10;
    private static final long NOT_EXISTING_BOOK_ID = 5;
    private static final long EXISTING_AUTHOR_ID = 10;
    private static final long EXISTING_GENRE_ID = 10;
    private static final String EXISTING_BOOK_TITLE = "Чистый код";
    private static final String EXISTING_AUTHOR_FIO = "Роберт Мартин";
    private static final String EXISTING_GENRE_NAME = "Программирование";
    private static final String ANY_BOOK_TITLE = "Какая-то книга";

    private static final Book EXISTING_BOOK = new Book()
            .setId(EXISTING_BOOK_ID)
            .setTitle(EXISTING_BOOK_TITLE)
            .setAuthor(new Author().setId(EXISTING_AUTHOR_ID).setFio(EXISTING_AUTHOR_FIO))
            .setGenre(new Genre().setId(EXISTING_GENRE_ID).setName(EXISTING_GENRE_NAME));

    @Autowired
    private BookServiceImpl sut;

    @Test
    @DisplayName("должен создавать книгу в хранилище")
    void shouldCreateBookInStorage() {
        var newBookTitle = "Настоящий программист";
        var actualBook = sut.create(newBookTitle, EXISTING_AUTHOR_FIO, EXISTING_GENRE_NAME);

        var expectedBook = sut.findByTitle(newBookTitle).orElseThrow();

        assertThat(actualBook).isEqualTo(expectedBook);

        sut.deleteById(actualBook.getId());
    }

    @Test
    @DisplayName("должен давать исключение, если автор не найден")
    void shouldThrowExceptionWhenAuthorNotFound() {
        assertThatCode(() -> sut.create(ANY_BOOK_TITLE, "Неизвестный автор", EXISTING_GENRE_NAME))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Отсутствует автор Неизвестный автор");
    }

    @Test
    @DisplayName("должен давать исключение, если жанр не найден")
    void shouldThrowExceptionWhenGenreNotFound() {
        assertThatCode(() -> sut.create(ANY_BOOK_TITLE, EXISTING_AUTHOR_FIO, "Неизвестный жанр"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Отсутствует жанр Неизвестный жанр");
    }

    @Test
    @DisplayName("должен давать исключение, если книга уже имеется")
    void shouldThrowExceptionWhenBookAlwaysExist() {
        assertThatCode(() -> sut.create(EXISTING_BOOK_TITLE, EXISTING_AUTHOR_FIO, EXISTING_GENRE_NAME))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Книга 'Чистый код' автора 'Роберт Мартин' уже имеется");
    }

    @Test
    @DisplayName("должен возвращать все книги из хранилища")
    void shouldReturnAllBooks() {
        var actualBooks = sut.getAll();

        var expectedBooks = List.of(EXISTING_BOOK);

        assertThat(actualBooks).isEqualTo(expectedBooks);
    }

    @Test
    @DisplayName("должен находить книгу по title в хранилище")
    void shouldFindBookByTitle() {
        var actualBook = sut.findByTitle(EXISTING_BOOK_TITLE).orElseThrow();

        assertThat(actualBook).isEqualTo(EXISTING_BOOK);
    }

    @Test
    @DisplayName("должен возвращать Optional.empty(), если книга не найдена по title")
    void shouldReturnOptionalEmptyWhenBookNotFoundByTitle() {
        var optActualBook = sut.findByTitle(ANY_BOOK_TITLE);

        assertThat(optActualBook).isEmpty();
    }

    @Test
    @DisplayName("должен находить книгу по id в хранилище")
    void shouldFindBookById() {
        var actualBook = sut.findById(EXISTING_BOOK_ID).orElseThrow();

        assertThat(actualBook).isEqualTo(EXISTING_BOOK);
    }

    @Test
    @DisplayName("должен возвращать Optional.empty(), если книга не найдена по id")
    void shouldReturnOptionalEmptyWhenBookNotFoundById() {
        var optActualBook = sut.findById(NOT_EXISTING_BOOK_ID);

        assertThat(optActualBook).isEmpty();
    }

    @Test
    @DisplayName("должен находить все книги по fio автора в хранилище")
    void shouldFindBooksByAuthorFio() {
        var actualBooks = sut.getByAuthorFio(EXISTING_AUTHOR_FIO);

        assertThat(actualBooks).isEqualTo(List.of(EXISTING_BOOK));
    }

    @Test
    @DisplayName("должен находить все книги по name жанра в хранилище")
    void shouldFindBooksByGenreName() {
        var actualBooks = sut.getByGenreName(EXISTING_GENRE_NAME);

        assertThat(actualBooks).isEqualTo(List.of(EXISTING_BOOK));
    }
}