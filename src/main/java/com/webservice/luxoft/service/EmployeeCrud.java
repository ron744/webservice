package com.webservice.luxoft.service;

import com.webservice.luxoft.model.Department;
import com.webservice.luxoft.model.EmployeeRequest;
import com.webservice.luxoft.model.EmployeeResponse;
import com.webservice.luxoft.repository.EmployeeRepository;
import com.webservice.luxoft.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Queue;
import java.util.stream.Collectors;

@Service
public class EmployeeCrud {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMessageSender employeeMessageSender;
    private final DepartmentCrud departmentCrud;

    @Autowired
    public EmployeeCrud(EmployeeRepository employeeRepository, EmployeeMessageSender employeeMessageSender, DepartmentCrud departmentCrud) {
        this.employeeRepository = employeeRepository;
        this.employeeMessageSender = employeeMessageSender;
        this.departmentCrud = departmentCrud;
    }

    public EmployeeResponse add(EmployeeRequest employeeRequest) throws NoSuchElementException {
        //TODO добавить обработку ошибок, если департамент не найден
        Department department = departmentCrud.getById(employeeRequest.getDepartmentId());
        if (department != null) {
            Employee employee = new Employee(employeeRequest.getName(), employeeRequest.getAge(), department);

            Employee savedEmployee = employeeRepository.save(employee);
            employeeMessageSender.send(savedEmployee);
            return new EmployeeResponse(employee);
        }

        return new EmployeeResponse();
    }

    public Queue<Employee> addAll(Queue<Employee> employees) {
        return (Queue<Employee>) employeeRepository.saveAll(employees);
    }

    public List<EmployeeResponse> getByName(String name) {
        return employeeRepository.findByNameLike(name)
                .stream()
                .map(EmployeeResponse::new)
                .collect(Collectors.toList());
    }

    public boolean update(Long id, Employee newEmployee) {
        Employee oldEmployee = employeeRepository.findById(id).orElseThrow(NoSuchElementException::new);

        if (!oldEmployee.equals(newEmployee)) {
            oldEmployee.setAge(newEmployee.getAge());
            oldEmployee.setName(newEmployee.getName());
            oldEmployee.setDepartment(newEmployee.getDepartment());

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
