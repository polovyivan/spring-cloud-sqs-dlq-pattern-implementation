package com.polovyi.ivan.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmailNotificationDTO extends NotificationDTO {

    private String email;

    public EmailNotificationDTO(@JsonProperty("fullName") String fullName, @JsonProperty("email") String email) {
        super(fullName);
        this.email = email;
    }

    @Override
    public String getNotificationMessage() {
        return "Dear " + fullName + " please confirm your email";
    }
}
