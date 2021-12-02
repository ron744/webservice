package com.webservice.luxoft.controller;

import com.webservice.luxoft.ecxeption.LoadingException;
import com.webservice.luxoft.model.Employee;
import com.webservice.luxoft.model.EmployeeRequest;
import com.webservice.luxoft.model.EmployeeResponse;
import com.webservice.luxoft.service.EmployeeLoadService;
import com.webservice.luxoft.service.EmployeeCrud;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;

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
    public EmployeeResponse addEmployee(@RequestBody EmployeeRequest employeeRequest) {
        try {
            EmployeeResponse employeeResponse = employeeCrud.add(employeeRequest);
            return employeeResponse;
        } catch (NoSuchElementException e) {
            log.error("File not found...", e);
            return new EmployeeResponse();
        }
//        return employeeCrud.add(employeeRequest).orElseThrow(NoClassDefFoundError::new);
    }

    @GetMapping("/getByName")
    public List<EmployeeResponse> getEmployeeByName(@RequestParam String name) {
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
