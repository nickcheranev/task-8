package ru.diasoft.ncheranev.otus.shell;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.diasoft.ncheranev.otus.domain.Genre;
import ru.diasoft.ncheranev.otus.service.GenreService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Класс GenreCommand")
class GenreCommandTest {
    @InjectMocks
    private GenreCommand sut;
    @Mock
    private GenreService genreService;

    @Test
    @DisplayName("должен возвращать форматированный список всех жанров")
    void shouldReturnAllGenresWithFormatting() {
        when(genreService.getAll()).thenReturn(List.of(
                new Genre().setId(1).setName("genre 1"),
                new Genre().setId(2).setName("genre 2")
                ));

        var actualAllGenres = sut.allGenres();

        assertThat(actualAllGenres).isEqualTo("[1]genre 1\n[2]genre 2");
    }

    @Test
    @DisplayName("должен вызывать сервис добавления жанра")
    void shouldCallServiceMethodAddGenre() {
        assertThatCode(() -> sut.addGenre("genre"))
                .doesNotThrowAnyException();

        verify(genreService).create("genre");
    }

    @Test
    @DisplayName("должен вызывать сервис удаления жанра по id")
    void shouldCallServiceMethodDeleteGenreById() {
        assertThatCode(() -> sut.deleteGenreById(1L))
                .doesNotThrowAnyException();

        verify(genreService).deleteById(1L);
    }
}