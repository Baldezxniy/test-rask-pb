package org.example.servicerest.service.impl;

import org.example.event.UserCollectedEvent;
import org.example.servicerest.messaging.UserEventProducer;
import org.example.servicerest.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserEventProducer userProducer;

    public UserServiceImpl(UserEventProducer userProducer) {
        this.userProducer = userProducer;
    }

    @Override
    public void initCollectProcess(Long id) {
        logger.info("Starting init process for User with ID: {}", id);
        var initialEvent = UserCollectedEvent.init(id);
        userProducer.sendCollectedEvent(initialEvent);
    }

}
