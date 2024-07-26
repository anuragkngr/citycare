package com.citycare.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@EnableAsync
@Service
public class KafkaConsumerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);

    @KafkaListener(topics = "your_topic_name", groupId = "group_id")
    @Async
    public void listen(String message, Acknowledgment acknowledgment) {
        logger.info("KafkaConsumerService listen message: {}", message);
        // Process the message
        acknowledgment.acknowledge();
    }
}
