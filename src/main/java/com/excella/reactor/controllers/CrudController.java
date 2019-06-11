package com.excella.reactor.controllers;

import com.excella.reactor.common.exceptions.ResourceNotFoundException;
import com.excella.reactor.service.CrudService;
import org.reactivestreams.Publisher;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

public abstract class CrudController<T, ID> {
    @GetMapping(value = "/", name = "Get all of resource", produces = "application/json")
    Publisher<T> getAllBooks() {
        return getService().all()
                .doOnSubscribe(result -> getLogger().info("Getting all"));
    }

    @GetMapping(value = "/{id}", name = "Get resource item by id", produces = "application/json")
    Publisher<T> getById(@PathVariable ID id) {
        return getService().byId(id)
                .doOnSubscribe(result -> getLogger().info("Getting id {}", id))
                .switchIfEmpty(
                        Mono.error(ResourceNotFoundException.of("No book was found"))
                );
    }

    @PostMapping(value = "/", name = "Add a new resource item", produces = "application/json")
    Publisher<T> create(@RequestBody @Validated T t) {
        return getService().save(t)
                .doOnSubscribe(result -> getLogger().info("Adding new item {}", t.toString()));
    }

    @PutMapping(value = "/{id}", name = "Update a resource by id", produces = "application/json")
    Publisher<T> update(@PathVariable ID id, @Validated T t) {
        return getService().update(id, t)
                .doOnSubscribe(result -> getLogger().info("Updating item {}", id));
    }

    @DeleteMapping(value = "/{id}", name = "Delete resource by id", produces = "application/json")
    Publisher<T> removeBookById(@PathVariable ID id) {
        return getService().delete(id)
                .doOnSubscribe(result -> getLogger().info("Deleting id {}", id));
    }


    abstract CrudService<T, ID> getService();
    abstract org.slf4j.Logger getLogger();
}
