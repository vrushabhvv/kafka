package com.deliveryboy.service;

import com.deliveryboy.config.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {

    private KafkaTemplate<String,String>  kafkaTemplate;

    public KafkaService(@Autowired KafkaTemplate<String,String>  kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public boolean sendMessage(String message){
        this.kafkaTemplate.send(AppConstants.LOCATION_TOPIC_NAME,message);
        return true;
    }
}
