package com.webservice.luxoft.controller;

import com.webservice.luxoft.model.EmployeeMessage;
import com.webservice.luxoft.service.EmployeeMessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/addMessage")
    public EmployeeMessage addEmployeeMessage(@RequestBody EmployeeMessage employeeMessage) {
        return employeeMessageSender.save(employeeMessage);
    }

    @GetMapping("/count")
    public String getCount() {
        return String.valueOf(employeeMessageSender.count());
    }
}
