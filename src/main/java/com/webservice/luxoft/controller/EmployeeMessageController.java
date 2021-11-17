package com.webservice.luxoft.controller;

import com.webservice.luxoft.service.EmployeeMessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/employeeMessage")
public class EmployeeMessageController {
    private final EmployeeMessageSender employeeMessageSender;

    @Autowired
    public EmployeeMessageController(EmployeeMessageSender employeeMessageSender) {
        this.employeeMessageSender = employeeMessageSender;
    }

    @GetMapping("/test")
    public String test() {
        return "Success";
    }

    @GetMapping("/getById")
    public String getEmployeeMessageById(@RequestParam Long id) {
        return employeeMessageSender.getEmployeeMessageById(id);
    }

    @GetMapping("/getAll")
    public void getAllMessages() {
        employeeMessageSender.findAll();
    }
}
