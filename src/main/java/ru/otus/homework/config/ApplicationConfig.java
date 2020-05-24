package ru.otus.homework.config;

import com.github.cloudyrock.mongock.Mongock;
import com.github.cloudyrock.mongock.SpringMongockBuilder;
import com.mongodb.MongoClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private static final String CHANGELOGS_PACKAGE = "ru.otus.homework.changelogs";

    private final MongoProperties mongoProperties;

    @Bean
    public Mongock mongock(MongoClient mongoClient) {
        return new SpringMongockBuilder(mongoClient, mongoProperties.getDatabase(), CHANGELOGS_PACKAGE)
                .build();
    }
}
