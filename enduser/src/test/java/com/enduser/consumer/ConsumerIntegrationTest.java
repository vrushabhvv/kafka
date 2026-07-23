package com.enduser.consumer;

import com.enduser.config.AppConstants;
import com.enduser.dto.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@Slf4j
public class ConsumerIntegrationTest {
    @Container
    static KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse(
            "confluentinc/cp-kafka:latest"));

    @DynamicPropertySource
    public static void initializeKafkaConfiguration(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers",kafkaContainer::getBootstrapServers);//iam saying i dont know where my kafka server is running get that info from kafka container
    }

    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;

    @Test
    public void testConsumeEvents(){
        log.info("testConsumeEvents method execution started");
        Customer customer = new Customer("123","test_user","madras","test@email.com");
        kafkaTemplate.send(AppConstants.LOCATION_TOPIC_CUSTOMER,customer);
        log.info("testConsumeEvents method execution ended");

        //need to wait to complete initializing,sending message and consume that, then assert/close test.
        //otherwise we will get consumer closed forcefully message.
        Awaitility.await()
                .atMost(Duration.ofSeconds(5))
                .pollInterval(Duration.ofMillis(200))
                .untilAsserted(() -> {
                    //assert statements
                });
    }
}
