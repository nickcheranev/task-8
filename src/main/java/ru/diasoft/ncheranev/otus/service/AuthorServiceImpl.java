package ru.diasoft.ncheranev.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.ncheranev.otus.dao.AuthorDao;
import ru.diasoft.ncheranev.otus.domain.Author;

import java.util.List;
import java.util.Optional;

/**
 * Реализация AuthorService
 *
 * @see AuthorService
 */
@Service
@RequiredArgsConstructor
@Transactional
public class AuthorServiceImpl implements AuthorService {
    private final AuthorDao authorDao;

    @Override
    public Author create(String fio) {
        if (authorDao.findByFio(fio).isPresent()) {
            throw new IllegalArgumentException("Автор " + fio + " уже имеется");
        }
        var id = authorDao.insert(new Author()
                .setFio(fio));

        return authorDao.findById(id).orElseThrow();
    }

    @Override
    public List<Author> getAll() {
        return authorDao.getAll();
    }

    @Override
    public Optional<Author> findById(long id) {
        return authorDao.findById(id);
    }

    @Override
    public Optional<Author> findByFio(String fio) {
        return authorDao.findByFio(fio);
    }

    @Override
    public void deleteById(long id) {
        try {
            authorDao.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Нельзя удалить автора, у которого имеются книги (id=" + id + ")", e);
        }
    }
}
