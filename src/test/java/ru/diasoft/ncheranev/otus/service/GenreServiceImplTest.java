package ru.diasoft.ncheranev.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.diasoft.ncheranev.otus.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@SpringBootTest
@DisplayName("Сервис для работы с жанрами")
class GenreServiceImplTest {

    private static final long EXISTED_GENRE_ID = 10;
    private static final String EXISTED_GENRE_NAME = "Программирование";
    private static final String NOT_EXISTED_GENRE_NAME = "Интернет";
    private static final Genre EXISTED_GENRE = new Genre().setId(EXISTED_GENRE_ID).setName(EXISTED_GENRE_NAME);

    @Autowired
    private GenreServiceImpl sut;

    @Test
    @DisplayName("должен создавать жанр, если такого нет")
    void shouldCreateGenreWhenGenreNotFoundByName() {
        var actualGenre = sut.create(NOT_EXISTED_GENRE_NAME);

        assertThat(actualGenre.getName()).isEqualTo(NOT_EXISTED_GENRE_NAME);
    }

    @Test
    @DisplayName("должен давать исключение, если такой жанр уже есть")
    void shouldThrowExceptionWhenGenreFoundByName() {
        assertThatCode(() -> sut.create(EXISTED_GENRE_NAME))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Жанр Программирование уже имеется");
    }

    @Test
    @DisplayName("должен возвращать все жанры")
    void shouldReturnAllGenres() {
        var actualGenres = sut.getAll();

        assertThat(actualGenres).isEqualTo(List.of(EXISTED_GENRE));
    }

    @Test
    @DisplayName("должен возвращать жанр по id")
    void shouldReturnGenreWithTargetId() {
        var actualGenre = sut.findById(EXISTED_GENRE_ID).orElseThrow();

        assertThat(actualGenre).isEqualTo(EXISTED_GENRE);
    }

    @Test
    @DisplayName("должен возвращать жанр по name")
    void shouldReturnGenreWithTargetName() {
        var actualGenre = sut.findByName(EXISTED_GENRE_NAME).orElseThrow();

        assertThat(actualGenre).isEqualTo(EXISTED_GENRE);
    }

    @Test
    @DisplayName("должен удалять имеющийся жанр по id")
    void shouldDeleteGenreById() {
        var newGenre = sut.create(NOT_EXISTED_GENRE_NAME);

        assertThat(sut.findById(newGenre.getId())).isPresent();

        sut.deleteById(newGenre.getId());

        assertThat(sut.findById(newGenre.getId())).isEmpty();
    }

    @Test
    @DisplayName("должен давать исключение, если по жанру имеются книги")
    void shouldThrowExceptionWhenTryDeleteGenreWithBooks() {
        assertThatCode(() -> sut.deleteById(EXISTED_GENRE_ID))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Нельзя удалить жанр, у которого имеются книги (id=10)");
    }
}