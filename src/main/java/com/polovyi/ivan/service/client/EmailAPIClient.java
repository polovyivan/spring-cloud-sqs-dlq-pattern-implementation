package com.polovyi.ivan.service.client;

import org.springframework.stereotype.Service;

@Service
public class EmailAPIClient extends  APIClient{


    @Override
    public String getClientName() {

        return "Email API";
    }
}
