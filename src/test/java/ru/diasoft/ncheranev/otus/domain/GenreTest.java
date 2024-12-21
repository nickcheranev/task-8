package ru.diasoft.ncheranev.otus.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Класс Genre")
class GenreTest {

    @Test
    @DisplayName("должен корректно преобразовываться в строку")
    void shouldCorrectConvertToString() {
        var actualGenreStringValue = new Genre()
                .setId(1)
                .setName("name")
                .toString();

        assertThat(actualGenreStringValue)
                .isEqualTo("[1]name");
    }
}