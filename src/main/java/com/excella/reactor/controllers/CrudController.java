package com.excella.reactor.controllers;

import com.excella.reactor.services.CrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public abstract class CrudController<S extends CrudService> {


  protected S service;


  protected CrudController(S service) {
    this.service = service;
  }

  @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
  public Flux getAll() {
    return service.getAll();
  }

  @GetMapping(value = "/{objId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono getById(@PathVariable Long objId) {
    return service.getById(objId);
  }







}
