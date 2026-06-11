package com.deliveryboy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.deliveryboy.service.KafkaService;

@RestController
@RequestMapping("/producer")
public class KafkaController {

    private KafkaService kafkaService;

    public KafkaController(@Autowired KafkaService kafkaService) {
        this.kafkaService = kafkaService;
    }

    @PostMapping("/location")
    public ResponseEntity<String> sendLocation(){
        String location = "(" +Math.round(Math.random())+","+Math.round(Math.random())+")";//sample example
        kafkaService.sendMessage(location);
        return ResponseEntity.ok(location);
    }
}
