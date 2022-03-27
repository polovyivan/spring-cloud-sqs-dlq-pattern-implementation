package com.polovyi.ivan.service.client;

import com.polovyi.ivan.dto.NotificationDTO;
import com.polovyi.ivan.enums.MessageStatus;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Slf4j
public abstract class APIClient {

    private Map<String, MessageStatus> messageTracker = new HashMap();

    public abstract String getClientName();

    public final void sendNotificationToCustomer(NotificationDTO dto) {

        MessageStatus messageStatus = messageTracker.get(dto.getId());

        if (messageStatus == null) {
            messageTracker.put(dto.getId(), getRandomAPIStatus());
        }

        switch (messageTracker.get(dto.getId())) {
        case SUCCESS:
            log.info("The message <{}> sent successfully!", dto.getNotificationMessage());
            messageTracker.remove(dto.getId());
            break;
        case TEMPORARY_ERROR:
            if (messageTracker.containsKey(dto.getId())) {
                messageTracker.put(dto.getId(), MessageStatus.SUCCESS);
            }
            log.error("{} is temporarily unavailable, retry one more time please :)", getClientName());
            throw new RuntimeException();
        case PERMANENT_ERROR:
            log.error("Unfortunately {} will be unavailable for a while :(", getClientName());
            throw new RuntimeException();
        }
    }

    private MessageStatus getRandomAPIStatus() {
        return List.of(MessageStatus.values()).get(new Random().nextInt(3));
    }
}
