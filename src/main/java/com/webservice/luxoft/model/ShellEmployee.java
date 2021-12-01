package com.webservice.luxoft.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ShellEmployee {
    private UUID uuid;
    private EmployeeView employeeView;
}
