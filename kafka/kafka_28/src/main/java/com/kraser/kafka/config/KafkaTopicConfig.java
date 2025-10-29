package com.kraser.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic kraserTopic() {
        return TopicBuilder.name("kraser").build();
    }

    @Bean
    public NewTopic kraserJsonTopic() {
        return TopicBuilder.name("kraser_json").build();
    }
}
