package com.polovyi.ivan.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.polovyi.ivan.dto.SmsNotificationDTO;
import com.polovyi.ivan.entity.converter.JsonObjectMapper;
import com.polovyi.ivan.service.client.SmsAPIClient;
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class SQSWithoutAttachedDLQListenerService {

    private final AmazonSQS amazonSQSClient;
    private final SmsAPIClient smsAPIClient;

    @Value("${cloud.aws.sqs.dlq-for-source-sqs-without-attached-dlq.url}")
    private String phoneDLQUrl;

    @Value("${cloud.aws.sqs.source-sqs-without-attached-dlq.reprocess-max-count}")
    private int reprocessMaxCount;

    @SqsListener(value = "${cloud.aws.sqs.source-sqs-without-attached-dlq.url}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void processConsentPaymentLogMessages(@Headers Map<String, String> headers,
            @Header("ApproximateReceiveCount") String approximateReceiveCount, @Payload String message) {

        log.info("Just to show that to obtain ApproximateReceiveCount {} the headers can be used too.",
                headers.get("ApproximateReceiveCount"));

        try {
            SmsNotificationDTO smsNotificationDTO = JsonObjectMapper.toObject(message, SmsNotificationDTO.class);

            smsAPIClient.sendNotificationToCustomer(smsNotificationDTO);
        } catch (RuntimeException ex) {
            handleException(approximateReceiveCount, message, ex);
        }
        log.info("Message processed successfully");
    }

    private void handleException(String approximateReceiveCount, String message, RuntimeException ex) {
        log.error("Error while trying to process message. Message approximateReceiveCount  {} ",
                approximateReceiveCount, ex.getLocalizedMessage());
        int approximateReceiveCountInt = Integer.parseInt(approximateReceiveCount);

        if (approximateReceiveCountInt <= reprocessMaxCount) {
            log.error("Returning message to a queue...");
            throw ex;
        }
        log.error("All {} tries are failed. Redirecting message to DLQ...", approximateReceiveCountInt);
        amazonSQSClient.sendMessage(phoneDLQUrl, message);
    }

}
