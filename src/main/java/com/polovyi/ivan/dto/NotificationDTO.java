package com.polovyi.ivan.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.polovyi.ivan.enums.MessageStatus;
import lombok.Data;

import java.util.List;
import java.util.Random;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class NotificationDTO {

    public String fullName;

    public MessageStatus status;

    public NotificationDTO(String fullName) {
        this.fullName = fullName;
        this.status = List.of(MessageStatus.values()).get(new Random().nextInt(3));
    }

    public abstract String getNotificationMessage();
}
