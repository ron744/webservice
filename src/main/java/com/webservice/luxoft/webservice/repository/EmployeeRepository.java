package com.webservice.luxoft.webservice.repository;

import com.webservice.luxoft.webservice.model.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    List<Employee> findByNameLike(String name);
}
