package com.deliveryboy.producer;

import com.deliveryboy.dto.Customer;
import com.deliveryboy.service.KafkaService;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class ProducerIntegrationTest {
    @Container
    static KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse(
            "confluentinc/cp-kafka:latest"));

    @DynamicPropertySource
    public static void initializeKafkaConfiguration(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers",kafkaContainer::getBootstrapServers);//iam saying i dont know where my kafka server is running get that info from kafka container
    }

    @Autowired
    private KafkaService producer;

    @Test   //this annotation should be from junit.jupiter from testcontainer
    public void testSendCustomer(){
        producer.sendCustomer(new Customer("123","test_user","madras","test@email.com"));

        // Then: Use Awaitility to handle asynchronous message processing lag
        Awaitility.await()
                .atMost(Duration.ofSeconds(5))
                .pollInterval(Duration.ofMillis(200))
                .untilAsserted(() -> {
                    //assert statements
                });
    }
}
