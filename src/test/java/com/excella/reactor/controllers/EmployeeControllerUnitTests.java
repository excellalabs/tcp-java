package com.excella.reactor.controllers;

import com.excella.reactor.domain.Employee;
import com.excella.reactor.service.EmployeeService;
import org.testng.annotations.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EmployeeControllerUnitTests {

    EmployeeService mockService = mock(EmployeeService.class);
    Employee mockEmployee1 = mock(Employee.class);
    Employee mockEmployee2 = mock(Employee.class);
    Employee mockEmployee3 = mock(Employee.class);

    EmployeeController employeeController = new EmployeeController(mockService);

    @Test
    public void contextLoads() {}

    @Test
    public void getAll_can_return_flux_of_multiple_employees(){
        when(mockService.all()).thenReturn(Flux.just(mockEmployee1, mockEmployee2, mockEmployee3));

        StepVerifier.create(
                mockService.all())
                .expectNext(mockEmployee1)
                .expectNext(mockEmployee2)
                .expectNext(mockEmployee3)
                .expectComplete()
                .verify();
    }

    @Test
    public void getAll_can_return_flux_of_no_employees(){
        when(mockService.all()).thenReturn(Flux.empty());

        StepVerifier.create(
                mockService.all())
                .expectComplete()
                .verify();
    }
}
