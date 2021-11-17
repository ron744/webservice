package com.webservice.luxoft.service;

import com.webservice.luxoft.repository.EmployeeRepository;
import com.webservice.luxoft.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

@Service
public class EmployeeCrud {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMessageSender employeeMessageSender;

    @Autowired
    public EmployeeCrud(EmployeeRepository employeeRepository, EmployeeMessageSender employeeMessageSender) {
        this.employeeRepository = employeeRepository;
        this.employeeMessageSender = employeeMessageSender;
    }

    public Employee add(Employee employee) {
        Employee savedEmployee = employeeRepository.save(employee);
        employeeMessageSender.send(savedEmployee);
        return savedEmployee;
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
            employeeMessageSender.send(oldEmployee);

            return true;
        } else {
            return false;
        }
    }

    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }
}
