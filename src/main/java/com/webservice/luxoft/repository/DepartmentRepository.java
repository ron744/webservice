package com.webservice.luxoft.repository;

import com.webservice.luxoft.model.Department;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, Long> {
    Optional<Department> findByName(String name);

//    @Modifying
//    @Query("delete from departments d where d.name = ?")
//    void deleteByName(String name);
}
