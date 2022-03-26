package com.polovyi.ivan.service;

import com.polovyi.ivan.dto.NotificationDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public abstract class SQSListenerService {

    private List<String> messageTrack = new ArrayList<>();

    final void sendNotificationToCustomer(NotificationDTO dto) {
        switch (dto.getStatus()) {
        case SUCCESS:
            log.info("The message {} sent successfully!", dto.getNotificationMessage());
            break;
        case TEMPORARY_ERROR:
            if (!messageTrack.contains(dto.getFullName())) {
                messageTrack.add(dto.getFullName());
                log.error("Notification API is temporary unavailable, retry one more time please :)");
                throw new RuntimeException();
            }
            log.info("The message {} sent successfully!", dto.getNotificationMessage());
            break;
        case PERMANENT_ERROR:
            log.error("Unfortunately notification API will be unavailable for a while :(");
            throw new RuntimeException();
        }
    }
}
