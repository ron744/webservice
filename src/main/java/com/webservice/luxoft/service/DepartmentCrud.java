package com.webservice.luxoft.service;

import com.webservice.luxoft.model.Department;
import com.webservice.luxoft.model.DepartmentRequest;
import com.webservice.luxoft.model.DepartmentResponse;
import com.webservice.luxoft.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class DepartmentCrud {
    public DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentCrud(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public DepartmentResponse add(DepartmentRequest departmentRequest) {

        if (departmentRequest.getParentDepartmentId() != null) {
            Optional<Department> parentDepOpt = departmentRepository.findById(departmentRequest.getParentDepartmentId());

            Department department = parentDepOpt
                    .map(value -> new Department(departmentRequest.getName(), departmentRequest.getDescription(), value, null))
                    .orElseGet(() -> new Department(departmentRequest.getName(), departmentRequest.getDescription(), null, null));

            departmentRepository.save(department);
            return new DepartmentResponse(department);
        }

        Department department = new Department(departmentRequest.getName(), departmentRequest.getDescription(), null, null);
        departmentRepository.save(department);

        return new DepartmentResponse(department);
    }

    public Department getByName(String name) {
        return departmentRepository.findByName(name).orElseThrow(NoSuchElementException::new);
    }

    public boolean update(String name, Department newDepartment) {
        Department oldDepartment = departmentRepository.findByName(name).orElseThrow(NoSuchElementException::new);
        if (!oldDepartment.equals(newDepartment)) {
            oldDepartment.setDescription(newDepartment.getDescription());
            oldDepartment.setMainDepartment(newDepartment.getMainDepartment());
            oldDepartment.setEmployees(newDepartment.getEmployees());

            departmentRepository.save(oldDepartment);

            return true;
        } else {
            return false;
        }
    }

    public Department getById(Long id) {
        return departmentRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public void delete(String name) {
//        departmentRepository.deleteByName(name);
    }
}
