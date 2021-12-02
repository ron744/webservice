package com.webservice.luxoft.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class EmployeeResponse {
    private Long id;
    private String name;
    private int age;
    private Long departmentId;
    private String departmentName;

    public EmployeeResponse(Employee employee) {
        this.id = employee.getId();
        this.name = employee.getName();
        this.age = employee.getAge();

        if (employee.getDepartment() != null) {
            this.departmentId = employee.getDepartment().getId();
            this.departmentName = employee.getDepartment().getName();
        } else {
            this.departmentId = null;
            this.departmentName = null;
        }
    }
}
