package com.webservice.luxoft.repository;

import com.webservice.luxoft.model.EmployeeMessage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeMessageRepository extends CrudRepository<EmployeeMessage, Long> {
    EmployeeMessage findFirstBySent(boolean sent);
}
