package com.excella.reactor.controllers;

import com.excella.reactor.common.exceptions.ResourceNotFoundException;
import com.excella.reactor.common.reactor.MonoUtils;
import com.excella.reactor.domain.Book;
import com.excella.reactor.repositories.BookRepository;
import com.excella.reactor.service.BookService;
import com.excella.reactor.service.CrudService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/book")
@Slf4j
public class BookController extends CrudController<Book, Long> {
    private final CrudService<Book, Long> bookService;

    @Autowired BookController(CrudService<Book, Long> bookService) {
        this.bookService = bookService;
    }

    protected CrudService<Book, Long> getService() {
        return bookService;
    }

    public Logger getLogger() {
        return log;
    }
}
