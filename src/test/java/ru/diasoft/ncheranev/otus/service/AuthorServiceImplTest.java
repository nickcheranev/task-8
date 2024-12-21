package ru.diasoft.ncheranev.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.diasoft.ncheranev.otus.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@SpringBootTest
@DisplayName("Сервис для работы с авторами")
class AuthorServiceImplTest {
    private static final long EXISTED_AUTHOR_ID = 10;
    private static final String EXISTED_AUTHOR_FIO = "Роберт Мартин";
    private static final String NOT_EXISTED_AUTHOR_FIO = "Крис Ричардсон";
    private static final Author EXISTED_AUTHOR = new Author().setId(EXISTED_AUTHOR_ID).setFio(EXISTED_AUTHOR_FIO);

    @Autowired
    private AuthorServiceImpl sut;

    @Test
    @DisplayName("должен создавать автора, если такого нет")
    void shouldCreateAuthorWhenNotFoundByFio() {
        var actualAuthor = sut.create(NOT_EXISTED_AUTHOR_FIO);

        assertThat(actualAuthor.getFio()).isEqualTo(NOT_EXISTED_AUTHOR_FIO);
    }

    @Test
    @DisplayName("должен давать исключение, если такой автор уже есть")
    void shouldThrowExceptionWhenFoundByFio() {
        assertThatCode(() -> sut.create(EXISTED_AUTHOR_FIO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Автор Роберт Мартин уже имеется");
    }

    @Test
    @DisplayName("должен возвращать всех авторов")
    void shouldReturnAllAuthors() {
        var actualAuthors = sut.getAll();

        assertThat(actualAuthors).isEqualTo(List.of(EXISTED_AUTHOR));
    }

    @Test
    @DisplayName("должен возвращать автора по id")
    void shouldReturnAuthorWithTargetId() {
        var actualAuthor = sut.findById(EXISTED_AUTHOR_ID).orElseThrow();

        assertThat(actualAuthor).isEqualTo(EXISTED_AUTHOR);
    }

    @Test
    @DisplayName("должен возвращать автора по fio")
    void shouldReturnAuthorWithTargetFio() {
        var actualAuthor = sut.findByFio(EXISTED_AUTHOR_FIO).orElseThrow();

        assertThat(actualAuthor).isEqualTo(EXISTED_AUTHOR);
    }

    @Test
    @DisplayName("должен удалять имеющегося автора по id")
    void shouldDeleteAuthorById() {
        var newAuthor = sut.create(NOT_EXISTED_AUTHOR_FIO);

        assertThat(sut.findById(newAuthor.getId())).isPresent();

        sut.deleteById(newAuthor.getId());

        assertThat(sut.findById(newAuthor.getId())).isEmpty();
    }

    @Test
    @DisplayName("должен давать исключение, если у имеющегося автора есть книги")
    void shouldThrowExceptionWhenTryDeleteAuthorWithBooks() {
        assertThatCode(() -> sut.deleteById(EXISTED_AUTHOR_ID))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Нельзя удалить автора, у которого имеются книги (id=10)");
    }
}