package org.example.mockservice1.messaging;

import org.example.event.UserCollectedEvent;
import org.example.mockservice1.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class UserListener {

    private static final String QUEUE_NAME = "users.collect-full-name.queue";

    private static final Logger logger = LoggerFactory.getLogger(UserListener.class);

    private final UserService userService;

    public UserListener(UserService userService) {
        this.userService = userService;
    }

    @JmsListener(destination = QUEUE_NAME, containerFactory = "jmsListenerContainerFactory")
    public void receiveInitEvent(UserCollectedEvent event) {
        logger.info("Received event from '{}' queue: {}", QUEUE_NAME, event);
        userService.processUserFullName(event);
    }

}
