package com.webservice.luxoft.websetvice.controller;

import com.webservice.luxoft.websetvice.ecxeption.LoadingException;
import com.webservice.luxoft.websetvice.model.Employee;
import com.webservice.luxoft.websetvice.service.EmployeeLoadService;
import com.webservice.luxoft.websetvice.service.EmployeeCrud;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping(path = "api/employee")
public class EmployeeController {
    private static final Logger log = Logger.getLogger(EmployeeController.class);

    private final EmployeeCrud employeeCrud;
    private final EmployeeLoadService employeeLoadService;

    @Autowired
    public EmployeeController(EmployeeCrud employeeCrud, EmployeeLoadService employeeLoadService) {
        this.employeeCrud = employeeCrud;
        this.employeeLoadService = employeeLoadService;
    }

    @GetMapping("/test")
    public String test() {
        return "Success";
    }

    @PostMapping("/add")
    public Employee addEmployee(@RequestBody Employee employee) {
        return employeeCrud.add(employee);
    }

    @GetMapping("/getByName")
    public List<Employee> getEmployeeByName(@RequestParam String name) {
        return employeeCrud.getByName(name);
    }

    @PutMapping("/updateById")
    public String updateEmployee(@RequestParam Long id, @RequestBody Employee newEmployee) {
        return employeeCrud.update(id, newEmployee) ? "Object has been updated" : "Object is equal to the object in the database";
    }

    @DeleteMapping("/deleteById")
    public void deleteEmployee(@RequestParam Long id) {
        employeeCrud.delete(id);
    }

    @GetMapping("/addFromFile")
    public String addEmployeeFromFile(@RequestParam String fileName) {
        try {
            employeeLoadService.loadFromXml(fileName);
        } catch (FileNotFoundException e) {
            log.error("File not found...", e);
            return "File not found...";
        } catch (LoadingException e) {
            log.error("My exception...", e);
            return "My exception...";
        }

        return "Added all elements to the database";
    }
}
