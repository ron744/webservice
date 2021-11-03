package com.webservice.luxoft.websetvice.service;

import com.webservice.luxoft.websetvice.model.Employee;
import com.webservice.luxoft.websetvice.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

@Service
public class EmployeeCrud {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeCrud(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee add(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Queue<Employee> addAll(Queue<Employee> employees) {
        return (Queue<Employee>) employeeRepository.saveAll(employees);
    }

    public List<Employee> getByName(String name) {
        return employeeRepository.findByNameLike(name);
    }

    public boolean update(Long id, Employee newEmployee) {
        Employee oldEmployee = employeeRepository.findById(id).orElseThrow(NoSuchElementException::new);

        if (!oldEmployee.equals(newEmployee)) {
            oldEmployee.setAge(newEmployee.getAge());
            oldEmployee.setName(newEmployee.getName());
            oldEmployee.setDivision(newEmployee.getDivision());

            employeeRepository.save(oldEmployee);

            return true;
        } else {
            return false;
        }
    }

    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }
}
