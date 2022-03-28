package com.polovyi.ivan.service;

import com.polovyi.ivan.dto.EmailNotificationDTO;
import com.polovyi.ivan.entity.converter.JsonObjectMapper;
import com.polovyi.ivan.service.client.EmailAPIClient;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class  SQSWithAttachedDLQListenerService {

    private final EmailAPIClient emailAPIClient;

    @SqsListener(value = "${cloud.aws.sqs.source-sqs-with-attached-dlq.url}")
    public void phoneSQSListener(String message) {
        log.info("Received message {}", message);
        EmailNotificationDTO emailNotificationDTO = JsonObjectMapper.toObject(message, EmailNotificationDTO.class);

        emailAPIClient.sendNotificationToCustomer(emailNotificationDTO);

        log.info("Message processed successfully");
    }

}
