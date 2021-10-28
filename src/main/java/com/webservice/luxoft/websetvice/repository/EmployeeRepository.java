package com.webservice.luxoft.websetvice.repository;

import com.webservice.luxoft.websetvice.model.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    List<Employee> findByNameLike(String name);
}
