package com.kraser.kafka.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    private final static Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "kraser", groupId = "myGroup")
    public void consume(String message) {
        LOGGER.info("=================================\nMessage received -> {}", message);
    }
}
