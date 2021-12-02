package com.webservice.luxoft.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class DepartmentRequest {
    private String name;
    private String description;
    private Long parentDepartmentId;
}
