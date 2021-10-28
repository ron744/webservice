package com.webservice.luxoft.websetvice.controller;

import com.webservice.luxoft.websetvice.model.Employee;
import com.webservice.luxoft.websetvice.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/employee")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/test")
    public String test() {
        return "Success";
    }

    @PostMapping("/add")
    public void addEmployee(@RequestBody Employee employee) {
        employeeRepository.save(employee);
    }

    @GetMapping("/getByName")
    public void getEmployeeByName(@RequestParam String name) {
        System.out.println(employeeRepository.findByNameLike(name));
    }
}
