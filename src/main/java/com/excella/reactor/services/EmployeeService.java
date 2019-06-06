package com.excella.reactor.services;

import com.excella.reactor.domain.Employee;
import com.excella.reactor.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("employeeService")
public class EmployeeService extends CrudService<Employee, EmployeeRepository> {
  @Autowired
  protected EmployeeService(EmployeeRepository repository) {
    super(repository);
  }
}
