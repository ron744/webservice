package com.webservice.luxoft.webservice.model;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ShellEmployee {
    private UUID uuid;
    private Employee employee;
}
