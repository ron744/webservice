package com.webservice.luxoft.websetvice.controller;

import com.webservice.luxoft.websetvice.model.Employee;
import com.webservice.luxoft.websetvice.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/test")
    public String test() {
        return "Success";
    }

    @PostMapping("/add")
    public Employee addEmployee(@RequestBody Employee employee) {
        return employeeService.addEmployee(employee);
    }

    @GetMapping("/getByName")
    public List<Employee> getEmployeeByName(@RequestParam String name) {
        return employeeService.getEmployeeByName(name);
    }

    @PutMapping("/updateById")
    public String updateEmployee(@RequestParam Long id, @RequestBody Employee newEmployee) {
        return employeeService.updateEmployee(id, newEmployee);
    }

    @DeleteMapping("/deleteById")
    public void deleteEmployee(@RequestParam Long id) {
        employeeService.deleteEmployee(id);
    }

    @GetMapping("/addFromFile")
    public void addEmployeeFromFile(@RequestParam String fileName) {
        employeeService.addEmployeeFromFile(fileName);
    }
}
