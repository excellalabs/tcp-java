package com.excella.reactor.services;

import com.excella.reactor.common.exceptions.EntityNotFoundException;
import com.excella.reactor.common.reactor.MonoUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.CrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public abstract class CrudService<D, R extends CrudRepository<D, Long>> {

  protected R repository;

  protected CrudService(R repository) {
    this.repository = repository;
  }

  public Flux<D> getAll() {
    return MonoUtils.retrieveAsList(repository::findAll)
        .doOnSubscribe(result -> log.info("Getting all objects"));
  }

  public Mono<D> getById(Long objId) {
    return MonoUtils.fromCallableOpt(() -> repository.findById(objId))
        .doOnSubscribe(result -> log.info("Getting obj id {}", objId))
        .switchIfEmpty(Mono.error(EntityNotFoundException.of("No object was found")));
  }



}
