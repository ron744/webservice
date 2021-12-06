package com.webservice.luxoft.controller;

import com.webservice.luxoft.ecxeption.LoadingException;
import com.webservice.luxoft.model.Employee;
import com.webservice.luxoft.model.EmployeeRequest;
import com.webservice.luxoft.model.EmployeeResponse;
import com.webservice.luxoft.service.EmployeeLoadService;
import com.webservice.luxoft.service.EmployeeCrud;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@RestController
@RequestMapping(path = "api/employee")
public class EmployeeController {
    private static final Logger log = Logger.getLogger(EmployeeController.class);

    private final EmployeeCrud employeeCrud;
    private final EmployeeLoadService employeeLoadService;
    private final Converter converter;

    @Autowired
    public EmployeeController(EmployeeCrud employeeCrud, EmployeeLoadService employeeLoadService, Converter converter) {
        this.employeeCrud = employeeCrud;
        this.employeeLoadService = employeeLoadService;
        this.converter = converter;
    }

    @GetMapping("/test")
    public String test() {
        return "Success";
    }

    @PostMapping("/add")
    public EmployeeResponse addEmployee(@RequestBody EmployeeRequest employeeRequest) {
        try {
            Employee employee = converter.requestConvert(employeeRequest);
            ResponseEntity<Employee> response = employeeCrud.add(employee);
            return new EmployeeResponse(Objects.requireNonNull(response.getBody()));

        } catch (NoSuchElementException e) {
            log.error("Department not found...", e);
            throw e;
        }
    }

    @GetMapping("/getByName")
    public List<EmployeeResponse> getEmployeeByName(@RequestParam String name) {
        return employeeCrud.getByName(name);
    }

    @PutMapping("/updateById")
    public ResponseEntity<String> updateEmployee(@RequestParam Long id, @RequestBody Employee newEmployee) {
        return employeeCrud.update(id, newEmployee) ? ResponseEntity.ok("Object has been updated") : ResponseEntity.status(409).body("Object is equal to the object in the database");
    }

    @DeleteMapping("/deleteById")
    public void deleteEmployee(@RequestParam Long id) {
        employeeCrud.delete(id);
    }

    @GetMapping("/addFromFile")
    public ResponseEntity<String> addEmployeeFromFile(@RequestParam String fileName) {
        try {
            employeeLoadService.loadFromXml(fileName);
        } catch (FileNotFoundException e) {
            log.error("File not found...", e);
            return ResponseEntity.status(404).body("File not found...");
        } catch (LoadingException e) {
            log.error("My exception...", e);
            return ResponseEntity.status(500).body("My exception...");
        }

        return ResponseEntity.ok("Added all elements to the database");
    }
}
