package com.bookmanagement.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository repository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.repository = bookRepository;
    }

    public List<Book> all() {
        return repository.findAll();
    }

    public Page<Book> findAll(Pageable paging) {
        return repository.findAll(paging);
    }

    public Page<Book> findAllByTitle(String title, Pageable paging) {
        return repository.findAllByTitle(title, paging);
    }

    public Book get(Long id) {
        try {
            return repository.findById(id).get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Book create(Book book) {
        return repository.save(book);
    }

    public Book update(Book book, Long id) {

        Optional<Book> toUpdate = repository.findById(id);

        if (toUpdate.isEmpty()) {
            throw new RuntimeException();
        }

        Book b = toUpdate.get();

        b.setTitle(book.getTitle());
        b.setAuthors(book.getAuthors());
        b.setDescription(book.getDescription());
        b.setLanguage(book.getLanguage());
        b.setPublished(book.getPublished());

        repository.save(b);

        return b;
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

}