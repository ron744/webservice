package com.webservice.luxoft;

import org.apache.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ConsumerClass {
    private final static Logger log = Logger.getLogger(ConsumerClass.class);

    @KafkaListener(topics = "test")
    public void consume(String message) {
        System.out.println("Consumer just receive the message: " + message);
    }
}
