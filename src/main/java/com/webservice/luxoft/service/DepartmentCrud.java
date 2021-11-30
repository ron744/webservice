package com.webservice.luxoft.service;

import com.webservice.luxoft.model.Department;
import com.webservice.luxoft.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class DepartmentCrud {
    public DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentCrud(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Department add(Department department) {
        return departmentRepository.save(department);
    }

    public Department getByName(String name) {
        return departmentRepository.findById(name).orElseThrow(NoSuchElementException::new);
    }

    public boolean update(String name, Department newDepartment) {
        Department oldDepartment = departmentRepository.findById(name).orElseThrow(NoSuchElementException::new);
        if (!oldDepartment.equals(newDepartment)) {
            oldDepartment.setDescription(newDepartment.getDescription());
//            oldDepartment.setParent(newDepartment.getParent());
            oldDepartment.setEmployees(newDepartment.getEmployees());

            departmentRepository.save(oldDepartment);

            return true;
        } else {
            return false;
        }
    }

    public void delete(String name) {
        departmentRepository.deleteById(name);
    }
}
