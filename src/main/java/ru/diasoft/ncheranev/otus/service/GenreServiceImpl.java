package ru.diasoft.ncheranev.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.ncheranev.otus.dao.GenreDao;
import ru.diasoft.ncheranev.otus.domain.Genre;

import java.util.List;
import java.util.Optional;

/**
 * Реализация GenreService
 *
 * @see GenreService
 */
@Service
@RequiredArgsConstructor
@Transactional
public class GenreServiceImpl implements GenreService {
    private final GenreDao genreDao;

    @Override
    public Genre create(String name) {
        if (genreDao.findByName(name).isPresent()) {
            throw new IllegalArgumentException("Жанр " + name + " уже имеется");
        }
        var id = genreDao.insert(new Genre()
                .setName(name));

        return genreDao.findById(id).orElseThrow();
    }

    @Override
    public List<Genre> getAll() {
        return genreDao.getAll();
    }

    @Override
    public Optional<Genre> findById(long id) {
        return genreDao.findById(id);
    }

    @Override
    public Optional<Genre> findByName(String name) {
        return genreDao.findByName(name);
    }

    @Override
    public void deleteById(long id) {
        try {
            genreDao.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Нельзя удалить жанр, у которого имеются книги (id=" + id + ")", e);
        }
    }
}
