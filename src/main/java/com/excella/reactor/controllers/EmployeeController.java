package com.excella.reactor.controllers;


import com.excella.reactor.common.exceptions.ResourceNotFoundException;
import com.excella.reactor.domain.Employee;
import com.excella.reactor.service.CrudService;
import com.excella.reactor.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/employee")
@Slf4j
public class EmployeeController {

  private EmployeeService service;


  @Autowired
  public EmployeeController(@Qualifier("employeeService") EmployeeService service) {
    this.service = service;
  }


  @GetMapping(value = "/", name = "Get all of resource", produces = "application/json")
  Publisher<Employee> getAll() {
    return getService().all()
        .doOnSubscribe(result -> log.info("Getting all"));
  }

  @GetMapping(value = "/{id}", name = "Get resource item by id", produces = "application/json")
  Publisher<Employee> getById(@PathVariable Long id) {
    return getService().byId(id)
        .doOnSubscribe(result -> log.info("Getting id {}", id))
        .switchIfEmpty(
            Mono.error(ResourceNotFoundException.of("No book was found"))
        );
  }

  @PostMapping(value = "/", name = "Add a new resource item", produces = "application/json")
  Publisher<Employee> create(@RequestBody @Validated Employee t) {
    return getService().save(t)
        .doOnSubscribe(result -> log.info("Adding new item {}", t));
  }

  @PutMapping(value = "/{id}", name = "Update a resource by id", produces = "application/json")
  Publisher<Employee> update(@PathVariable Long id, @Validated Employee t) {
    return getService().update(id, t)
        .doOnSubscribe(result -> log.info("Updating item {}", id));
  }

  @DeleteMapping(value = "/{id}", name = "Delete resource by id", produces = "application/json")
  Publisher<Employee> removeById(@PathVariable Long id) {
    return getService().delete(id)
        .doOnSubscribe(result -> log.info("Deleting id {}", id));
  }


  CrudService<Employee> getService() {
    return this.service;
  }


}