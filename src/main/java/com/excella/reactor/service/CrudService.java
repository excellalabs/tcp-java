package com.excella.reactor.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface CrudService<T, ID> {
    Flux<T> all();
    Mono<T> byId(ID id);
    Mono<T> save(T e);
    Mono<T> update(ID id, T e);
    Mono<T> delete(ID id);
}
