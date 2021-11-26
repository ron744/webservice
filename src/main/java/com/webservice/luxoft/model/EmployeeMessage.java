package com.webservice.luxoft.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class EmployeeMessage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JsonProperty("employee")
    private String employee;
    @JsonProperty("sendDate")
    private String sendDate;
    @JsonProperty("sent")
    private boolean sent;

    public EmployeeMessage(String employee, String sendDate, boolean sent) {
        this.employee = employee;
        this.sendDate = sendDate;
        this.sent = sent;
    }
}
