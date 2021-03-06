package com.webservice.luxoft.controller;

import com.webservice.luxoft.model.Department;
import com.webservice.luxoft.model.DepartmentRequest;
import com.webservice.luxoft.model.DepartmentResponse;
import com.webservice.luxoft.service.DepartmentCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/department")
public class DepartmentController {
    private final DepartmentCrud departmentCrud;

    @Autowired
    public DepartmentController(DepartmentCrud departmentCrud) {
        this.departmentCrud = departmentCrud;
    }

    @PostMapping("/add")
    public DepartmentResponse add(@RequestBody DepartmentRequest departmentRequest) {
        return departmentCrud.add(departmentRequest);
    }

    @GetMapping("/getByName")
    public Department getByName(@RequestParam String name) {
        return departmentCrud.getByName(name);
    }

    @PostMapping("/updateByName")
    public boolean update(@RequestParam String name, @RequestBody Department department) {
        return departmentCrud.update(name, department);
    }

    @DeleteMapping("/deleteByName")
    public void deleteByName(@RequestParam String name) {
        departmentCrud.delete(name);
    }
}
