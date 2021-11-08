package com.webservice.luxoft.websetvice.controller;

import com.webservice.luxoft.websetvice.ecxeption.MyException;
import com.webservice.luxoft.websetvice.model.Employee;
import com.webservice.luxoft.websetvice.service.EmployeeFileWorking;
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
    private final EmployeeFileWorking employeeFileWorking;

    @Autowired
    public EmployeeController(EmployeeCrud employeeCrud, EmployeeFileWorking employeeFileWorking) {
        this.employeeCrud = employeeCrud;
        this.employeeFileWorking = employeeFileWorking;
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
            employeeFileWorking.readXml(fileName);
        } catch (FileNotFoundException e) {
            log.error("File not found...", e);
            return "File not found...";
        } catch (MyException e) {
            log.error("My exception...", e);
            return "My exception...";
        }

        return "Added all elements to the database";
    }
}
