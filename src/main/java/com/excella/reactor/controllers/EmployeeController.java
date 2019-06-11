package com.excella.reactor.controllers;


import com.excella.reactor.domain.Employee;
import com.excella.reactor.service.CrudService;
import com.excella.reactor.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
@Slf4j
public class EmployeeController extends CrudController<Employee> {

  private EmployeeService service;

  @Autowired
  public EmployeeController(@Qualifier("employeeService") EmployeeService service) {
    this.service = service;
  }

  CrudService<Employee> getService() {
    return this.service;
  }


}