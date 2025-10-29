package com.kraser.kafka.controller;

import com.kraser.kafka.dto.UserDto;
import com.kraser.kafka.kafka.JsonKafkaProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/kafka")
public class JsonMessageController {

    private JsonKafkaProducer jsonKafkaProducer;

    public JsonMessageController(JsonKafkaProducer jsonKafkaProducer) {
        this.jsonKafkaProducer = jsonKafkaProducer;
    }

    @PostMapping(path = "/publish")
    public ResponseEntity<String> publish(@RequestBody UserDto userDto) {
        jsonKafkaProducer.sendMessage(userDto);
        return ResponseEntity.ok("Json Message sent to the kafka topic");
    }
}
