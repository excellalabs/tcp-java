package com.excella.reactor.service;

import com.excella.reactor.common.reactor.MonoUtils;
import com.excella.reactor.domain.DomainModel;
import org.springframework.data.repository.CrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class CrudServiceImpl<T extends DomainModel<ID>, ID> implements CrudService<T, ID> {
    public Flux<T> all() {
        return MonoUtils.retrieveAsList(getRepository()::findAll);
    }

    public Mono<T> byId(ID id) {
        return MonoUtils.fromCallableOpt(() -> getRepository().findById(id));
    }

    public Mono<T> update(ID id, T t) {
        return byId(id)
                .map(p -> {
                    t.setId(id);
                    return t;
                })
                .flatMap(this::save);
    }

    public Mono<T> save(T t) {
        return MonoUtils.fromCallable(() -> getRepository().save(t));
    }

    public Mono<T> delete(ID id) {
        return byId(id)
                .flatMap(p -> MonoUtils.fromCallable(() -> {
                    getRepository().delete(p);
                    return p;
                }));
    }

    abstract CrudRepository<T, ID> getRepository();
}