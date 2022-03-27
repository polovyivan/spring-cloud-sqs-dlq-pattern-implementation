package com.polovyi.ivan.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.polovyi.ivan.dto.CustomerRequest;
import com.polovyi.ivan.dto.CustomerResponse;
import com.polovyi.ivan.entity.CustomerEntity;
import com.polovyi.ivan.entity.converter.JsonObjectMapper;
import com.polovyi.ivan.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AmazonSQS amazonSQSClient;

    @Value("${cloud.aws.sqs.source-sqs-with-attached-dlq.url}")
    private String  emailSQSUrl;

    @Value("${cloud.aws.sqs.source-sqs-without-attached-dlq.url}")
    private String phoneSQSUrl;

    public List<CustomerResponse> getAllCustomers() {
        log.info("Getting all customers...");
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(customerRepository.findAll().iterator(), Spliterator.ORDERED),
                false).map(CustomerResponse::valueOf).collect(Collectors.toList());
    }

    @SneakyThrows
    public void createCustomer(CustomerRequest customerRequest) {
        log.info("Creating customer...");

        CustomerEntity customer = CustomerEntity.valueOf(customerRequest);

        customerRepository.save(customer);
        putMessagesToSQS(customerRequest);
    }

    private void putMessagesToSQS(CustomerRequest customerRequest) {

        String objectAsString = JsonObjectMapper.toString(customerRequest);

        if (customerRequest.getPhoneNumber() != null) {
            log.info("Sending message to queue {} ...", phoneSQSUrl);
            amazonSQSClient.sendMessage(phoneSQSUrl, objectAsString);
        }

        log.info("Sending message to queue {} ...", emailSQSUrl);
        amazonSQSClient.sendMessage(emailSQSUrl, objectAsString);
    }
}

