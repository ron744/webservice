package com.webservice.luxoft.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeRequest {
    private String name;
    private int age;
    private Long departmentId;
}
