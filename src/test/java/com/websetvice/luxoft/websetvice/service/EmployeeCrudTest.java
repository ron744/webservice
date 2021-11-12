package com.websetvice.luxoft.websetvice.service;

import com.webservice.luxoft.websetvice.model.Employee;
import com.webservice.luxoft.websetvice.repository.EmployeeRepository;
import com.webservice.luxoft.websetvice.service.EmployeeCrud;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class EmployeeCrudTest {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeCrud employeeCrud;


    @Test
    public void updateTrue() {
        assertThat(employeeRepository).isNotNull();

        Employee employee = new Employee("superName", 44, "new division");

        employeeCrud.add(employee);
//
//        employee.setName("name");
//
//        boolean b = employeeCrud.update(1L, employee);
    }
}
