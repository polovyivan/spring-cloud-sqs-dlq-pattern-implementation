package com.polovyi.ivan.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class NotificationDTO {

    public String id;
    public String fullName;

    public NotificationDTO(String fullName, String id) {
        this.fullName = fullName;
        this.id = id;
    }

    public abstract String getNotificationMessage();
}
