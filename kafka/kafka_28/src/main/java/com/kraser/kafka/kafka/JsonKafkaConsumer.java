package com.kraser.kafka.kafka;

import com.kraser.kafka.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class JsonKafkaConsumer {

    private final static Logger LOGGER = LoggerFactory.getLogger(JsonKafkaConsumer.class);

    @KafkaListener(topics = "kraser_json", groupId = "myGroup")
    public void consume(UserDto userDto) {
        LOGGER.info("=================================\nJson Message received -> {}", userDto.toString());
    }
}
