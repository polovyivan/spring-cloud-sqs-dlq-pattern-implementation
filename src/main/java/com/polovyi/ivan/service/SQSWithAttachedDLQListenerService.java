package com.polovyi.ivan.service;

import com.polovyi.ivan.dto.EmailNotificationDTO;
import com.polovyi.ivan.entity.converter.JsonObjectMapper;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class  SQSWithAttachedDLQListenerService extends SQSListenerService {

    @SqsListener(value = "${cloud.aws.sqs.source-sqs-with-attached-dlq.url}")
    public void phoneSQSListener(String message) {
        log.info("Received message {}", message);
        EmailNotificationDTO emailNotificationDTO = JsonObjectMapper.toObject(message, EmailNotificationDTO.class);

        sendNotificationToCustomer(emailNotificationDTO);

        log.info("Message processed successfully");
    }

}
