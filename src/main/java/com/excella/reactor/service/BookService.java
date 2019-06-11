package com.excella.reactor.service;

import com.excella.reactor.domain.Book;
import com.excella.reactor.repositories.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class BookService extends CrudServiceImpl<Book, Long> {
    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public BookRepository getRepository() {
        return this.repository;
    }
}
