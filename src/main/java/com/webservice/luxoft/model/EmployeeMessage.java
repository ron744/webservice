package com.webservice.luxoft.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class EmployeeMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String employee;
    private String sendDate;

    public EmployeeMessage() {}

    public EmployeeMessage(String employee, String sendDate) {
        this.employee = employee;
        this.sendDate = sendDate;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    @Override
    public String toString() {
        return "EmployeeMessage{" +
                "employee=" + employee +
                ", sendDate='" + sendDate + '\'' +
                '}';
    }
}
