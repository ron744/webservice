package com.webservice.luxoft.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.webservice.luxoft.model.Employee;
import com.webservice.luxoft.model.EmployeeMessage;
import com.webservice.luxoft.repository.EmployeeMessageRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

// в рамках этого класса нужно создать messageSender, который будет проверять изменения таблицы EmployeeMessage и отправлять сообщение в kafka
@Service
public class EmployeeMessageSender implements MessageSender<Employee> {
    private final static Logger log = Logger.getLogger(EmployeeMessageSender.class);
    private final EmployeeMessageRepository employeeMessageRepository;
//    private final MessageSender<EmployeeMessage> sender;

    @Autowired
    public EmployeeMessageSender(EmployeeMessageRepository employeeMessageRepository) {
        this.employeeMessageRepository = employeeMessageRepository;
    }

    // вместо employee в employeeMessage нужно сохранять json
    @Override
    public void send(Employee employee) {
        LocalDateTime now = LocalDateTime.now();

        EmployeeMessage employeeMessage = new EmployeeMessage(objectToJson(employee), now.toString());

        employeeMessageRepository.save(employeeMessage);
    }

    @Scheduled(fixedDelay = 3000)
    private void scanNewEmployee() {
        // планировщик должен запускаться раз в 3 секунды, сканировать таблицу бд на предмет новых записей и, если они есть отправлять их в очередь Кафка
        System.out.println("scheduler");
    }

    private String objectToJson(Employee employee) {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = null;
        try {
            json = ow.writeValueAsString(employee);
        } catch (JsonProcessingException e) {
            log.error("Exception while json parsing...", e);
        }

        return json;
    }

    public String getEmployeeMessageById(Long id) {
        EmployeeMessage message = employeeMessageRepository.findById(id).orElseThrow(NoSuchElementException::new);
        return message.toString();
    }

    public void findAll() {
        List<EmployeeMessage> messageList = (List<EmployeeMessage>) employeeMessageRepository.findAll();
        for (EmployeeMessage message : messageList) {
            System.out.println(message);
        }
    }
}
