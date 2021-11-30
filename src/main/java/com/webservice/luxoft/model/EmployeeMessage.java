package com.webservice.luxoft.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "employee_messages")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class EmployeeMessage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "employee_message_id")
    private Long id;
    @JsonProperty("idEmployee")
    @Column(name = "employee_id")
    private Long idEmployee;
    @JsonProperty("version")
    private Integer version;
    @JsonProperty("sendDate")
    private Timestamp sendDate;
    @JsonProperty("sent")
    private boolean sent;

    public EmployeeMessage(Long idEmployee, Integer version, Timestamp sendDate, boolean sent) {
        this.idEmployee = idEmployee;
        this.version = version;
        this.sendDate = sendDate;
        this.sent = sent;
    }
}
