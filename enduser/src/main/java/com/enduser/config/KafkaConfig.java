package com.enduser.config;

import com.enduser.dto.Customer;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import tools.jackson.databind.ObjectMapper;

@Configuration
public class KafkaConfig {

    //doing manual deserialization, we had jsonDeserializer in spring 2 but it got removed.
    ObjectMapper mapper = new ObjectMapper();

    //will create 4 consumer , subscribing to 3 partition
    //iam creating 4 methods, we can create 4 classes and method inside that also
    @KafkaListener(topics = AppConstants.LOCATION_TOPIC_NAME,groupId = AppConstants.GROUP_ID)//topic name should be same of producer and group id provided in properties file
    public void updateLocation1(String location){
    	System.out.println("c1 "+location);
    }

    @KafkaListener(topics = AppConstants.LOCATION_TOPIC_NAME,groupId = AppConstants.GROUP_ID)//topic name should be same of producer and group id provided in properties file
    public void updateLocation2(String location){
        System.out.println("c2 "+location);
    }

    @KafkaListener(topics = AppConstants.LOCATION_TOPIC_NAME,groupId = AppConstants.GROUP_ID)//topic name should be same of producer and group id provided in properties file
    public void updateLocation3(String location){
        System.out.println("c3 "+location);
    }

    @KafkaListener(topics = AppConstants.LOCATION_TOPIC_NAME,groupId = AppConstants.GROUP_ID)//topic name should be same of producer and group id provided in properties file
    public void updateLocation4(String location){
        System.out.println("c4 "+location);
    }

    @KafkaListener(topics = AppConstants.LOCATION_TOPIC_CUSTOMER,groupId = AppConstants.GROUP_CUSTOMER_ID)
    public void updateCustomer(String json){
        Customer customer = mapper.readValue(json,Customer.class);
        System.out.println("hi "+customer);
    }
}
