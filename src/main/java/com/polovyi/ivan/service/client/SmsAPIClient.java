package com.polovyi.ivan.service.client;

import org.springframework.stereotype.Service;

@Service
public class SmsAPIClient extends APIClient{


    @Override
    public String getClientName() {
        return "Sms API";
    }
}
