package org.example.mockservice1.service.impl;

import org.example.event.UserCollectedEvent;
import org.example.mockservice1.messaging.UserEventProducer;
import org.example.mockservice1.service.ExternalFullNameService;
import org.example.mockservice1.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final ExternalFullNameService externalFullNameService;
    private final UserEventProducer userEventProducer;

    public UserServiceImpl(
            ExternalFullNameService externalFullNameService,
            UserEventProducer userEventProducer) {
        this.externalFullNameService = externalFullNameService;
        this.userEventProducer = userEventProducer;
    }

    @Override
    public void processUserFullName(UserCollectedEvent event) {

        var responseDto = externalFullNameService.fetchByUserId(event.getUserId());
        event.setFirstName(responseDto.firstName());
        event.setLastName(responseDto.lastName());
        event.setMiddleName(responseDto.middleName());

        userEventProducer.sendCollectedEvent(event);
    }

}
