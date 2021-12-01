package com.webservice.luxoft.service;

import com.webservice.luxoft.model.Department;
import com.webservice.luxoft.model.EmployeeView;
import com.webservice.luxoft.repository.EmployeeRepository;
import com.webservice.luxoft.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Queue;

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

    public Optional<Employee> add(EmployeeView employeeView) {
        Department department = departmentCrud.getById(employeeView.getDepartmentId());
        if (department != null) {
            Employee employee = new Employee(employeeView.getName(), employeeView.getAge(), department);

            Employee savedEmployee = employeeRepository.save(employee);
            employeeMessageSender.send(savedEmployee);
            return Optional.of(savedEmployee);
        }

        return Optional.empty();
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
