package com.webservice.luxoft.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class DepartmentResponse {
    private Long id;
    private String name;
    private String description;
    private Long parentDepartmentId;
    private Collection<String> employeeNames;

    public DepartmentResponse(Department department) {
        this.id = department.getId();
        this.name = department.getName();
        this.description = department.getDescription();

        Collection<Employee> employeeList = department.getEmployees();
        if (employeeList != null) {
            this.employeeNames = department.getEmployees().stream().map(Employee::getName).collect(Collectors.toList());
        } else {
            this.employeeNames = null;
        }

        this.parentDepartmentId = department.getMainDepartment() == null ? null : department.getMainDepartment().getId();
    }
}
