package com.deliveryboy.service;

import com.deliveryboy.config.AppConstants;
import com.deliveryboy.dto.Customer;
import com.deliveryboy.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaService {

    private KafkaTemplate<String, String> kafkaTemplate1;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public KafkaService(@Autowired KafkaTemplate<String, String> kafkaTemplate1) {
        this.kafkaTemplate1 = kafkaTemplate1;
    }

    public boolean sendMessage(String message) {
//        CompletableFuture<SendResult<String, String>> send =
        this.kafkaTemplate1.send(AppConstants.LOCATION_TOPIC_NAME, 1, null, message);
//        send.whenComplete((result,ex)->{
//            if(ex==null){
//                System.out.println("location updated,"+result);//result contains topic,partition,offset and metadata
//                //printing
//                System.out.println("topic "+result.getRecordMetadata().topic());
//                System.out.println("partition "+result.getRecordMetadata().partition());
//                System.out.println("offset "+result.getRecordMetadata().offset());
//
//            }else{
//                System.out.println("something went wrong");
//            }
//        });
        return true;
    }

    public boolean sendCustomer(Customer customer) {
        String json = objectMapper.writeValueAsString(customer);
        kafkaTemplate1.send(AppConstants.LOCATION_TOPIC_CUSTOMER, json);
        return true;
    }

    public boolean sendSpecificPartition(String message) {
        this.kafkaTemplate1.send(AppConstants.MESSAGE, 2, null, message);
        return true;
    }

    //    example to error handling
    public boolean sendUser(User user) {
        String json = objectMapper.writeValueAsString(user);
        kafkaTemplate1.send(AppConstants.USER_TOPIC_1, json);
        return true;
    }
}
