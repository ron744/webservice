package com.webservice.luxoft.repository;

import com.webservice.luxoft.model.Department;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, String> {
}
