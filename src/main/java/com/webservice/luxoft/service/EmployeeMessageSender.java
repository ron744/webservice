package com.webservice.luxoft.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.webservice.luxoft.model.Employee;
import com.webservice.luxoft.model.EmployeeMessage;
import com.webservice.luxoft.repository.EmployeeMessageRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EmployeeMessageSender implements MessageSender<Employee> {
    private final static Logger log = Logger.getLogger(EmployeeMessageSender.class);
    private final static String TOPIC = "test";
    private final EmployeeMessageRepository employeeMessageRepository;
    private final KafkaTemplate kafkaTemplate;

    @Autowired
    public EmployeeMessageSender(EmployeeMessageRepository employeeMessageRepository, KafkaTemplate kafkaTemplate) {
        this.employeeMessageRepository = employeeMessageRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(Employee employee) {
        LocalDateTime now = LocalDateTime.now();

        EmployeeMessage employeeMessage = new EmployeeMessage(objectToJson(employee), now.toString(), false);

        employeeMessageRepository.save(employeeMessage);
    }

    @Scheduled(fixedDelay = 3000)
    @Transactional
    private void scanNewEmployee() {
        System.out.println("scheduler");

        EmployeeMessage em = employeeMessageRepository.findFirstBySent(false);
        if (em != null) {
            em.setSent(true);
            employeeMessageRepository.save(em);
            kafkaTemplate.send(TOPIC, em.toString());
        }
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
