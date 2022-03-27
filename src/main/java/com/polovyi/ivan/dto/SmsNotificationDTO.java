package com.polovyi.ivan.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SmsNotificationDTO extends NotificationDTO {

    private String phoneNumber;

    public SmsNotificationDTO(@JsonProperty("fullName") String fullName,
            @JsonProperty("phoneNumber") String phoneNumber, @JsonProperty("id") String id) {
        super(fullName, id);
        this.phoneNumber = phoneNumber;
    }

    @JsonIgnore
    @Override
    public String getNotificationMessage() {
        return "Dear " + fullName + ", please confirm your phone number";
    }

}
