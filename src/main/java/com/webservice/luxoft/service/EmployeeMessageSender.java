package com.webservice.luxoft.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.webservice.luxoft.model.Employee;
import com.webservice.luxoft.model.EmployeeMessage;
import com.webservice.luxoft.repository.EmployeeMessageRepository;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ArrayBlockingQueue;

@Service
public class EmployeeMessageSender implements MessageSender<Employee> {
    private final static Logger log = Logger.getLogger(EmployeeMessageSender.class);
    private final static String TOPIC = "test";
    private final EmployeeMessageRepository employeeMessageRepository;
    private final KafkaTemplate kafkaTemplate;
    private final ArrayBlockingQueue<EmployeeMessage> senderList = new ArrayBlockingQueue<>(200);

    @Autowired
    public EmployeeMessageSender(EmployeeMessageRepository employeeMessageRepository, KafkaTemplate kafkaTemplate) {
        this.employeeMessageRepository = employeeMessageRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(Employee employee) {
        LocalDateTime now = LocalDateTime.now();

        EmployeeMessage employeeMessage = new EmployeeMessage(objectToJson(employee), now.toString());

        employeeMessageRepository.save(employeeMessage);

        senderList.add(employeeMessage);
    }

    @Scheduled(fixedDelay = 3000)
    private void scanNewEmployee() throws InterruptedException {
        System.out.println("scheduler");
        kafkaTemplate.send(TOPIC, senderList.take().toString());
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

    @Bean
    public NewTopic topic() {
        return new NewTopic("test", 3, (short) 1);
    }
}
