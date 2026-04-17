package ru.diasoft.ncheranev.otus.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.diasoft.ncheranev.otus.domain.Author;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao для работы с авторами")
@JdbcTest
@Import(AuthorDaoJdbc.class)
class AuthorDaoJdbcTest {
    private static final int EXPECTED_AUTHORS_COUNT = 1;
    private static final long EXISTING_AUTHOR_ID = 10;
    private static final long NOT_EXISTING_AUTHOR_ID = 20;
    private static final String EXISTING_AUTHOR_FIO = "Роберт Мартин";
    private static final String ABSENT_AUTHOR_FIO = "Лев Толстой";

    @Autowired
    private AuthorDaoJdbc sut;

    @Test
    @DisplayName("должен возвращать ожидаемый список авторов")
    void shouldReturnExpectedAuthorList() {
        var expectedAuthor = new Author()
                .setId(EXISTING_AUTHOR_ID)
                .setFio(EXISTING_AUTHOR_FIO);

        var actualAuthorList = sut.getAll();

        assertThat(actualAuthorList)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(expectedAuthor);
    }

    @Test
    @DisplayName("должен добавлять автора в БД")
    void shouldInsertAuthor() {
        var actualAuthor = new Author()
                .setFio("Лев Толстой");

        var id = sut.insert(actualAuthor);
        actualAuthor.setId(id);

        var expectedAuthor = sut.findById(id).orElseThrow();

        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("должен возвращать ожидаемого автора по id при его наличии")
    @Test
    void shouldReturnExpectedAuthorById() {
        var expectedAuthor = new Author()
                .setId(EXISTING_AUTHOR_ID)
                .setFio(EXISTING_AUTHOR_FIO);

        var actualAuthor = sut.findById(expectedAuthor.getId()).orElseThrow();

        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("должен возвращать Optional.empty() в случае отсутствия автора при поиске по id")
    @Test
    void shouldReturnOptionalEmptyWhenAuthorNotFoundById() {
        var actualAuthor = sut.findById(NOT_EXISTING_AUTHOR_ID);

        assertThat(actualAuthor).isEmpty();
    }

    @DisplayName("должен возвращать ожидаемого автора по fio при его наличии")
    @Test
    void shouldReturnExpectedAuthorByTitle() {
        var expectedAuthor = new Author()
                .setId(EXISTING_AUTHOR_ID)
                .setFio(EXISTING_AUTHOR_FIO);

        var actualAuthor = sut.findByFio(expectedAuthor.getFio()).orElseThrow();

        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("должен возвращать Optional.empty() в случае отсутствия при поиске автора по fio")
    @Test
    void shouldReturnOptionalEmptyWhenAuthorNotFoundByFio() {
        var actualAuthor = sut.findByFio(ABSENT_AUTHOR_FIO);

        assertThat(actualAuthor).isEmpty();
    }

    @DisplayName("должен удалять заданного автора по id")
    @Test
    void shouldCorrectDeleteAuthorById() {
        var newAuthor = new Author().setFio("Иван Тургенев");
        var newAuthorId = sut.insert(newAuthor);

        assertThat(sut.findById(newAuthorId)).isPresent();

        sut.deleteById(newAuthorId);

        assertThat(sut.findById(newAuthorId)).isEmpty();
    }

    @DisplayName("должен возвращать ожидаемое количество авторов в БД")
    @Test
    void shouldReturnExpectedAuthorCount() {
        var actualAuthorCount = sut.count();

        assertThat(actualAuthorCount).isEqualTo(EXPECTED_AUTHORS_COUNT);
    }
}