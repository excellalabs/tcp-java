package com.excella.reactor.controllers;


import com.excella.reactor.services.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
@Slf4j
public class EmployeeController extends CrudController<EmployeeService> {

  @Autowired
  public EmployeeController(@Qualifier("employeeService") EmployeeService service) {
    super(service);
  }
}
