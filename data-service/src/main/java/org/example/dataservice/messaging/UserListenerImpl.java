package org.example.dataservice.messaging;

import org.example.dataservice.service.UserService;
import org.example.event.UserCollectedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class UserListenerImpl {

    private static final String QUEUE_NAME = "users.collect-storage.queue";

    private static final Logger logger = LoggerFactory.getLogger(UserListenerImpl.class);

    private final UserService userService;

    public UserListenerImpl(UserService userService) {
        this.userService = userService;
    }

    @JmsListener(destination = QUEUE_NAME, containerFactory = "jmsListenerContainerFactory")
    public void receiveInitEvent(UserCollectedEvent event) {
        logger.info("Received event from '{}' queue: {}", QUEUE_NAME, event);
        userService.createUserFromEvent(event);
    }

}
