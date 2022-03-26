package com.polovyi.ivan.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.polovyi.ivan.enums.MessageStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class SmsNotificationDTO extends NotificationDTO {

    private String phoneNumber;

    public SmsNotificationDTO(@JsonProperty("fullName") String fullName, @JsonProperty("phoneNumber")  String phoneNumber) {
        super(fullName);
        this.phoneNumber = phoneNumber;
    }

    @JsonIgnore
    @Override
    public String getNotificationMessage() {
        return "Dear " + fullName + " please confirm your phone number";
    }

}
