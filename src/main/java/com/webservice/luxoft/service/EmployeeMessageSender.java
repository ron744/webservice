package com.webservice.luxoft.service;


import com.webservice.luxoft.model.Employee;
import com.webservice.luxoft.model.EmployeeMessage;
import com.webservice.luxoft.repository.EmployeeMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
public class EmployeeMessageSender implements MessageSender<Employee> {
    private final EmployeeMessageRepository employeeMessageRepository;
//    private final MessageSender<EmployeeMessage> sender;

    @Autowired
    public EmployeeMessageSender(EmployeeMessageRepository employeeMessageRepository) {
        this.employeeMessageRepository = employeeMessageRepository;
    }

    @Override
    public void send(Employee employee) {
        LocalDateTime now = LocalDateTime.now();

        EmployeeMessage employeeMessage = new EmployeeMessage(employee.toString(), now.toString());

        EmployeeMessage savedEmployeeMessage = employeeMessageRepository.save(employeeMessage);
        //запись в бд в отдельную таблицу, о том, что мы хотим отправить. И есть информация, о том, когда отправка произошла.
        // и есть шедуллер, который просматривает записи в бд, которые не были отправлены
        System.out.println(savedEmployeeMessage);
    }

    public String getEmployeeMessageById(Long id) {
        return employeeMessageRepository.findById(id).orElseThrow(NoSuchElementException::new).toString();
    }

    public long count() {
        return employeeMessageRepository.count();
    }

    public EmployeeMessage save(EmployeeMessage employeeMessage) {
        return employeeMessageRepository.save(employeeMessage);
    }
}
