package com.bookmanagement.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getAll(@RequestParam(required = false) String title, @RequestParam(required = false, defaultValue = "0") int page, @RequestParam(required = false, defaultValue = "10") int size, @RequestParam(required = false, defaultValue = "id") String sortBy) {

        /*
        @RequestParam HashMap<String, String> params
        //HashMap<String, String> params=;

        System.out.println(params);
        System.out.println(params.size());
        System.out.println(params.isEmpty());

        int page = Integer.valueOf(params.get("page"));
        int size = Integer.valueOf(params.get("size"));
        String sortBy = params.get("sortBy");
        String title = params.get("title");
        */

        try {
            Pageable paging = PageRequest.of(page, size, Sort.by(sortBy));
            Page<Book> pages;
            if (title == null)
                pages = bookService.findAll(paging);
            else
                pages = bookService.findAllByTitle(title, paging);
            return pages.getContent();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}")
    public Book get(@PathVariable Long id) {
        try {
            return bookService.get(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping
    Book create(@RequestBody Book book) {
        return bookService.create(book);
    }

    @PutMapping("/{id}")
    Book update(@RequestBody Book book, @PathVariable Long id) {
        return bookService.update(book, id);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id) {
        bookService.delete(id);
    }
}
