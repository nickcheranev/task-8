package ru.diasoft.ncheranev.otus.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.diasoft.ncheranev.otus.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao для работы с жанрами")
@JdbcTest
@Import(GenreDaoJdbc.class)
class GenreDaoJdbcTest {
    private static final int EXPECTED_GENRES_COUNT = 1;
    private static final long EXISTING_GENRE_ID = 10;
    private static final long NOT_EXISTING_GENRE_ID = 20;
    private static final String EXISTING_GENRE_NAME = "Программирование";
    private static final String ABSENT_GENRE_NAME = "Комикс";

    @Autowired
    private GenreDaoJdbc sut;

    @Test
    @DisplayName("должен возвращать ожидаемый список жанров")
    void shouldReturnExpectedGenreList() {
        var expectedGenre = new Genre()
                .setId(EXISTING_GENRE_ID)
                .setName(EXISTING_GENRE_NAME);

        var actualGenreList = sut.getAll();

        assertThat(actualGenreList)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(expectedGenre);
    }

    @Test
    @DisplayName("должен добавлять жанр в БД")
    void shouldInsertGenre() {
        var expectedGenre = new Genre()
                .setName("Пьеса");

        var id = sut.insert(expectedGenre);
        var actualGenre = sut.findById(id).orElseThrow();
        expectedGenre.setId(id);

        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);

        sut.deleteById(id);
    }

    @DisplayName("должен возвращать ожидаемый жанр по id при его наличии")
    @Test
    void shouldReturnExpectedGenreById() {
        var expectedGenre = new Genre()
                .setId(EXISTING_GENRE_ID)
                .setName(EXISTING_GENRE_NAME);

        var actualGenre = sut.findById(expectedGenre.getId());

        assertThat(actualGenre.orElseThrow()).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("должен возвращать Optional.empty() в случае отсутствия жанра при поиске по id")
    @Test
    void shouldReturnOptionalEmptyWhenGenreNotFoundById() {
        var actualAuthor = sut.findById(NOT_EXISTING_GENRE_ID);

        assertThat(actualAuthor).isEmpty();
    }

    @DisplayName("должен возвращать ожидаемый жанр по name при его наличии")
    @Test
    void shouldReturnExpectedGenreByTitle() {
        var expectedGenre = new Genre()
                .setId(EXISTING_GENRE_ID)
                .setName(EXISTING_GENRE_NAME);

        var actualGenre = sut.findByName(expectedGenre.getName());

        assertThat(actualGenre.orElseThrow()).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("должен возвращать Optional.empty() в случае отсутствия при поиске жанра по name")
    @Test
    void shouldReturnOptionalEmptyWhenGenreNotFoundByFio() {
        var actualGenre = sut.findByName(ABSENT_GENRE_NAME);

        assertThat(actualGenre).isEmpty();
    }

    @DisplayName("должен удалять заданный жанр по id")
    @Test
    void shouldCorrectDeleteGenreById() {
        var newGenre = new Genre().setName("Интернет");
        var id = sut.insert(newGenre);

        assertThat(sut.findById(id)).isPresent();

        sut.deleteById(id);

        assertThat(sut.findById(id)).isEmpty();
    }

    @DisplayName("должен возвращать ожидаемое количество жанров в БД")
    @Test
    void shouldReturnExpectedAuthorCount() {
        var actualAuthorCount = sut.count();

        assertThat(actualAuthorCount).isEqualTo(EXPECTED_GENRES_COUNT);
    }
}