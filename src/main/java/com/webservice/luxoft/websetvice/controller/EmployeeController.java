package com.webservice.luxoft.websetvice.controller;

import com.webservice.luxoft.websetvice.model.Employee;
import com.webservice.luxoft.websetvice.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public Employee addEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    @GetMapping("/getByName")
    public List<Employee> getEmployeeByName(@RequestParam String name) {
        return employeeRepository.findByNameLike(name);
    }

    @PutMapping("/updateById")
    public String updateEmployee(@RequestParam Long id, @RequestBody Employee newEmployee) {
        Optional<Employee> optEmployee = employeeRepository.findById(id);
        if (optEmployee.isPresent()) {
            Employee oldEmployee = optEmployee.get();

            if (!oldEmployee.equals(newEmployee)) {
                oldEmployee.setAge(newEmployee.getAge());
                oldEmployee.setName(newEmployee.getName());
                oldEmployee.setDivision(newEmployee.getDivision());

                employeeRepository.save(oldEmployee);

                return "Object has been updated";
            }

            return "Object is equal to the object in the database";
        }

        return "Object not found";
    }

    @DeleteMapping("/deleteById")
    public void deleteEmployee(@RequestParam Long id) {
        employeeRepository.deleteById(id);
    }
}
