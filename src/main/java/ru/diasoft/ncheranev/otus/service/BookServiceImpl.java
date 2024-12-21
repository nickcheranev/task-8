package ru.diasoft.ncheranev.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.ncheranev.otus.dao.AuthorDao;
import ru.diasoft.ncheranev.otus.dao.BookDao;
import ru.diasoft.ncheranev.otus.dao.GenreDao;
import ru.diasoft.ncheranev.otus.domain.Book;

import java.util.List;
import java.util.Optional;

/**
 * Реализация BookService
 *
 * @see BookService
 */
@Service
@RequiredArgsConstructor
@Transactional
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    @Override
    public Book create(String title, String authorFio, String genreName) {
        var author = authorDao.findByFio(authorFio).orElseThrow(() -> new IllegalArgumentException("Отсутствует автор " + authorFio));
        var genre = genreDao.findByName(genreName).orElseThrow(() -> new IllegalArgumentException("Отсутствует жанр " + genreName));

        if (bookDao.existsByTitleAndAuthorId(title, author.getId())) {
            throw new IllegalArgumentException("Книга '" + title + "' автора '" + authorFio + "' уже имеется");
        }

        var id = bookDao.insert(new Book()
                .setTitle(title)
                .setAuthor(author)
                .setGenre(genre));

        var book = bookDao.findById(id).orElseThrow();
        fillReferences(book);
        return book;
    }

    @Override
    public List<Book> getAll() {
        return bookDao.getAll().stream().peek(this::fillReferences).toList();
    }

    @Override
    public Optional<Book> findById(long id) {
        var optBook = bookDao.findById(id);
        if (optBook.isPresent()) {
            var book = optBook.get();
            fillReferences(book);
            return Optional.of(book);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Book> findByTitle(String title) {
        var optBook = bookDao.findByTitle(title);
        if (optBook.isPresent()) {
            var book = optBook.get();
            fillReferences(book);
            return Optional.of(book);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<Book> getByAuthorFio(String authorFio) {
        var optAuthor = authorDao.findByFio(authorFio);

        return optAuthor.map(author -> bookDao.getAllByAuthorId(author.getId())
                .stream().peek(this::fillReferences).toList()).orElseGet(List::of);
    }

    @Override
    public List<Book> getByGenreName(String genreName) {
        var optGenre = genreDao.findByName(genreName);

        return optGenre.map(genre -> bookDao.getAllByGenreId(genre.getId())
                .stream().peek(this::fillReferences).toList()).orElseGet(List::of);
    }

    @Override
    public void deleteById(long id) {
        bookDao.deleteById(id);
    }

    private void fillReferences(Book refBook) {
        refBook.setAuthor(authorDao.findById(refBook.getAuthor().getId()).orElseThrow());
        refBook.setGenre(genreDao.findById(refBook.getGenre().getId()).orElseThrow());
    }
}
