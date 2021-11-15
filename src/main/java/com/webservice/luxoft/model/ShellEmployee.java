package com.webservice.luxoft.model;

import java.util.UUID;

public class ShellEmployee {
    private UUID uuid;
    private Employee employee;

    public ShellEmployee(UUID uuid, Employee employee) {
        this.uuid = uuid;
        this.employee = employee;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "ShellEmployee{" +
                "uuid=" + uuid +
                ", employee=" + employee +
                '}';
    }
}
