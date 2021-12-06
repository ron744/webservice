package com.webservice.luxoft.controller;

import com.webservice.luxoft.model.Department;
import com.webservice.luxoft.model.Employee;
import com.webservice.luxoft.model.EmployeeRequest;
import com.webservice.luxoft.service.DepartmentCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class Converter {

    private final DepartmentCrud departmentCrud;

    @Autowired
    public Converter(DepartmentCrud departmentCrud) {
        this.departmentCrud = departmentCrud;
    }

    public Employee requestConvert(EmployeeRequest employeeRequest) throws NoSuchElementException {
        Department department = departmentCrud.getById(employeeRequest.getDepartmentId());
        if (department == null) {
            return new Employee(employeeRequest.getName(), employeeRequest.getAge(), null);
        }

        return new Employee(employeeRequest.getName(), employeeRequest.getAge(), department);
    }
}
