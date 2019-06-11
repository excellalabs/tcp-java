package com.excella.reactor.service.impl;

import com.excella.reactor.domain.Employee;
import com.excella.reactor.repositories.EmployeeRepository;
import com.excella.reactor.service.EmployeeService;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;


@Service
public class EmployeeServiceImpl implements EmployeeService {

  private EmployeeRepository repository;


  public EmployeeServiceImpl(EmployeeRepository repository) {
    this.repository = repository;
  }

  public CrudRepository<Employee, Long> getRepository() {
    return this.repository;
  }
}
