package ru.otus.homework.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("spring.data.mongodb")
@Data
public class MongoProperties {
    private int port;
    private String database;
    private String uri;
}
