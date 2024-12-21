package ru.diasoft.ncheranev.otus.shell;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.diasoft.ncheranev.otus.domain.Author;
import ru.diasoft.ncheranev.otus.domain.Book;
import ru.diasoft.ncheranev.otus.domain.Genre;
import ru.diasoft.ncheranev.otus.service.BookService;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Класс BookCommand")
class BookCommandTest {
    @InjectMocks
    private BookCommand sut;
    @Mock
    private BookService bookService;

    @Test
    @DisplayName("должен возвращать форматированный список всех книг")
    void shouldReturnAllBooksWithFormatting() {
        when(bookService.getAll()).thenReturn(List.of(
                new Book()
                        .setId(1)
                        .setTitle("title 1")
                        .setAuthor(new Author().setId(11).setFio("fio 1"))
                        .setGenre(new Genre().setId(111).setName("genre 1")),
                new Book()
                        .setId(2)
                        .setTitle("title 2")
                        .setAuthor(new Author().setId(22).setFio("fio 2"))
                        .setGenre(new Genre().setId(222).setName("genre 2"))
                ));

        var actualAllAuthors = sut.allBooks();

        assertThat(actualAllAuthors).isEqualTo("[1]title 1 [11]fio 1 [111]genre 1\n" +
                "[2]title 2 [22]fio 2 [222]genre 2");
    }

    @Test
    @DisplayName("должен вызывать сервис добавления книги")
    void shouldCallServiceMethodAddBook() {
        assertThatCode(() -> sut.addBook("title", "author", "genre"))
                .doesNotThrowAnyException();

        verify(bookService).create("title", "author", "genre");
    }

    public static Stream<Arguments> shouldThrowExceptionWhenTitleOrAuthorOrGenreNotSet_methodSource() {
        return Stream.of(
                Arguments.of(null, null, null),
                Arguments.of("title", null, null),
                Arguments.of("title", "author", null),
                Arguments.of(null, "author", null),
                Arguments.of(null, null, "genre"),
                Arguments.of(null, "author", "genre")
        );
    }

    @ParameterizedTest
    @MethodSource("shouldThrowExceptionWhenTitleOrAuthorOrGenreNotSet_methodSource")
    @DisplayName("должен выдать исключение, если название книги, автор или жанр не заданы")
    void shouldThrowExceptionWhenTitleOrAuthorOrGenreNotSet(String title, String authorFio, String genreName) {
        assertThatCode(() -> sut.addBook(title, authorFio, genreName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Должны быть заданы название книги, автор и жанр");
    }

    @Test
    @DisplayName("должен вызывать сервис удаления книги по id")
    void shouldCallServiceMethodDeleteBookById() {
        assertThatCode(() -> sut.deleteBookById(1L))
                .doesNotThrowAnyException();

        verify(bookService).deleteById(1L);
    }

    @Test
    @DisplayName("должен возвращать книги заданного автора")
    void shouldReturnBooksOfAuthor() {
        when(bookService.getByAuthorFio("author")).thenReturn(List.of(
                new Book()
                        .setId(1)
                        .setTitle("title 1")
                        .setAuthor(new Author().setId(11).setFio("author"))
                        .setGenre(new Genre().setId(111).setName("genre 1")),
                new Book()
                        .setId(2)
                        .setTitle("title 2")
                        .setAuthor(new Author().setId(22).setFio("author"))
                        .setGenre(new Genre().setId(222).setName("genre 2"))
        ));

        var actualAllAuthors = sut.allBooksByAuthor("author");

        assertThat(actualAllAuthors).isEqualTo("[1]title 1 [11]author [111]genre 1\n" +
                "[2]title 2 [22]author [222]genre 2");
    }

    @Test
    @DisplayName("должен возвращать книги заданного жанра")
    void shouldReturnBooksOfGenre() {
        when(bookService.getByGenreName("genre")).thenReturn(List.of(
                new Book()
                        .setId(1)
                        .setTitle("title 1")
                        .setAuthor(new Author().setId(11).setFio("fio 1"))
                        .setGenre(new Genre().setId(111).setName("genre")),
                new Book()
                        .setId(2)
                        .setTitle("title 2")
                        .setAuthor(new Author().setId(22).setFio("fio 2"))
                        .setGenre(new Genre().setId(222).setName("genre"))
        ));

        var actualAllAuthors = sut.allBooksByGenre("genre");

        assertThat(actualAllAuthors).isEqualTo("[1]title 1 [11]fio 1 [111]genre\n" +
                "[2]title 2 [22]fio 2 [222]genre");
    }
}