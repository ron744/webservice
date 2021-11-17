package com.webservice.luxoft.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class EmployeeMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JsonProperty("employee")
    private String employee;
    @JsonProperty("sendDate")
    private String sendDate;

    public EmployeeMessage() {}

    public EmployeeMessage(String employee, String sendDate) {
        this.employee = employee;
        this.sendDate = sendDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
                "id=" + id +
                ", employee='" + employee + '\'' +
                ", sendDate='" + sendDate + '\'' +
                '}';
    }
}
