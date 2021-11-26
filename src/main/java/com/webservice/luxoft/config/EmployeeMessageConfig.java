package com.webservice.luxoft.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmployeeMessageConfig {
    @Bean
    public NewTopic topic() {
        return new NewTopic("test", 3, (short) 1);
    }
}
