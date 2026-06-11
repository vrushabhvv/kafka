package com.enduser.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
public class KafkaConfig {

    @KafkaListener(topics = AppConstants.LOCATION_TOPIC_NAME,groupId = AppConstants.GROUP_ID)//topic name should be same of producer and group id provided in properties file
    public void updateLocation(String location){
    	System.out.println(location);
    }
}
