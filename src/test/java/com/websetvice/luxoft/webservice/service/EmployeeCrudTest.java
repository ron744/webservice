package com.websetvice.luxoft.webservice.service;

import com.webservice.luxoft.webservice.model.Employee;
import com.webservice.luxoft.webservice.repository.EmployeeRepository;
import com.webservice.luxoft.webservice.service.EmployeeCrud;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EmployeeCrudTest {
    private final EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
    private final EmployeeCrud employeeCrud = new EmployeeCrud(employeeRepository);
    private static Employee oldEmployee;
    private static final long id = 1L;

    @BeforeEach
    public void setUp() {
        oldEmployee = new Employee("superName", 44, "new division");
        oldEmployee.setId(id);
    }

    @Test
    public void updateTrue() {
        Mockito.doReturn(Optional.of(oldEmployee)).when(employeeRepository).findById(id);

        Employee newEmployee = new Employee("name", 44, "new division");
        assertThat(true).isEqualTo(employeeCrud.update(id, newEmployee));
    }

    @Test
    public void updateIdNotExist() {
        Mockito.doReturn(Optional.of(oldEmployee)).when(employeeRepository).findById(id);

        Employee newEmployee = new Employee("name", 44, "new division");
        Assertions.assertThrows(NoSuchElementException.class, () -> employeeCrud.update(10L, newEmployee));
    }

    @Test
    public void updateOldEmployeeAndNewEmployeeEquals() {
        Mockito.doReturn(Optional.of(oldEmployee)).when(employeeRepository).findById(id);

        Employee newEmployee = new Employee("superName", 44, "new division");
        assertThat(true).isNotEqualTo(employeeCrud.update(id, newEmployee));
    }

//    @Test
    public void updateNewEmployeeIsEmpty() {
        Mockito.doReturn(Optional.of(oldEmployee)).when(employeeRepository).findById(id);

        assertThat(true).isEqualTo(employeeCrud.update(id, new Employee()));
    }

    @Test
    public void updateIdIsNull() {
        Mockito.doReturn(Optional.of(oldEmployee)).when(employeeRepository).findById(id);

        Employee newEmployee = new Employee("name", 44, "new division");
        Assertions.assertThrows(NoSuchElementException.class, () -> employeeCrud.update(null, newEmployee));
    }

    @Test
    public void updateEmployeeIsNull() {
        Mockito.doReturn(Optional.of(oldEmployee)).when(employeeRepository).findById(id);

        Assertions.assertThrows(NullPointerException.class, () -> employeeCrud.update(id, null));
    }
}
