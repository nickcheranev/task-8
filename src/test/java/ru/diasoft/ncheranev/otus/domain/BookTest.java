package ru.diasoft.ncheranev.otus.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Класс Book")
class BookTest {

    @Test
    @DisplayName("должен корректно преобразовываться в строку")
    void shouldCorrectConvertToString() {
        var actualBookStringValue = new Book()
                .setId(1)
                .setTitle("title")
                .setAuthor(new Author().setId(2).setFio("fio"))
                .setGenre(new Genre().setId(3).setName("name"))
                .toString();

        assertThat(actualBookStringValue)
                .isEqualTo("[1]title [2]fio [3]name");
    }
}