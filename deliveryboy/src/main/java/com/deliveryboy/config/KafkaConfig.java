package com.deliveryboy.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import static com.deliveryboy.config.AppConstants.*;

@Configuration
public class KafkaConfig {
    //if we wont define this NewTopic then spring boot will create topic automatically with 1 partition
    //for production it is advisable to configure manually in bean so that we will have more control
    //if we dont want then we need to create topic with command where we can define partition count,replication factor,offset
    //we can use either of these approach , at the end creation of topic should be our explicit.
    @Bean
    public NewTopic topic(){
        return TopicBuilder.name(LOCATION_TOPIC_NAME)
                .partitions(3)
//                .replicas()
                .build();
    }

    @Bean
    public NewTopic messageTopic(){
        return TopicBuilder.name(MESSAGE)
                .partitions(6)
                .replicas(1)
                .build();
    }
    @Bean
    public NewTopic userTopic(){
        return TopicBuilder.name(USER_TOPIC_1)
                .partitions(3)
                .replicas(1)
                .build();
    }


}
