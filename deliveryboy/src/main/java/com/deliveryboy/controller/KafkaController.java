package com.deliveryboy.controller;

import com.deliveryboy.dto.Customer;
import com.deliveryboy.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
        String location = "location";
        for(int i = 0;i<100;i++) {
            //sample example
            kafkaService.sendMessage(location+":->"+i);
        }
        return ResponseEntity.ok("location sent successfully");
    }

    @PostMapping("/customer")
    public ResponseEntity<String> sendCustomer(@RequestBody Customer customer){
        kafkaService.sendCustomer(customer);
        return ResponseEntity.ok("customer sent successfully");
    }

    @PostMapping("/specificpartition")
    public ResponseEntity<String> send(){
        for(int i = 0;i<10;i++) {
            //sample example
            kafkaService.sendSpecificPartition("hi");
        }
        return ResponseEntity.ok("message sent successfully");
    }

//    kafka error handling
    @PostMapping("/user")
    public ResponseEntity<String> sendUser(@RequestBody User user){
        kafkaService.sendUser(user);
        return ResponseEntity.ok("user sent successfully");
    }
}
