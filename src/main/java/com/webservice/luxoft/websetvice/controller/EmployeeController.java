package com.webservice.luxoft.websetvice.controller;

import com.webservice.luxoft.websetvice.model.Employee;
import com.webservice.luxoft.websetvice.service.EmployeeFileWorking;
import com.webservice.luxoft.websetvice.service.EmployeeCrud;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.io.IOException;
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
            employeeFileWorking.someMethod(fileName);
        } catch (FileNotFoundException e) {
            log.error("File not found...", e.fillInStackTrace());
            return "File not found...";
        } catch (XMLStreamException e) {
            log.error("Exception while creating new XMLStreamReader...", e.fillInStackTrace());
            return "Exception while creating new XMLStreamReader...";
        } catch (IOException e) {
            log.error("Exception while reading xml file...", e.fillInStackTrace());
            return  "Exception while reading xml file...";
        }

        return "Added all elements to the database.";
    }
}
