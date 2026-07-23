package com.enduser.config;

import com.enduser.dto.Customer;
import com.enduser.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.annotation.TopicPartition;
import tools.jackson.databind.ObjectMapper;

@Configuration
@Slf4j
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

    @KafkaListener(groupId = AppConstants.GROUP_ID,
            topicPartitions = {@TopicPartition(topic = AppConstants.MESSAGE,partitions = {"2"})}
    )
    public void updateSpecificPartition(String message){
        System.out.println("c4 "+message);
    }

    //problematic code,error handling
    @RetryableTopic(attempts = "4") //creates 3 retry topic on server
    @KafkaListener(topics = AppConstants.USER_TOPIC_1,groupId = AppConstants.GROUP_USER_ID)
    public void ConsumeUser(String json){
        User user = mapper.readValue(json,User.class);
        //to understand based on email i will throw error
        if(user.getEmail().startsWith("m")){
            log.error("user is not allowed ",user);
            throw new RuntimeException("this is not allowed");
        }
//        this block is just for example, assume in this place there is call to db/aws s3 that time we will loose
//        messages from producer
        log.info("user consumed successfully"+user);
    }

    @DltHandler
    public void listenDlt(String json){//maintain same parameter
        log.info("listenDlt received"+json);
    }
}
