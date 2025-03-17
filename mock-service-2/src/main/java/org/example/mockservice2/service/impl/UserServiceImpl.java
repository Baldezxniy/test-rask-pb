package org.example.mockservice2.service.impl;

import org.example.event.UserCollectedEvent;
import org.example.mockservice2.messaging.UserEventProducer;
import org.example.mockservice2.service.ExternalAddressService;
import org.example.mockservice2.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final ExternalAddressService externalAddressService;
    private final UserEventProducer userEventProducer;

    public UserServiceImpl(
            ExternalAddressService externalAddressService,
            UserEventProducer userEventProducer) {
        this.externalAddressService = externalAddressService;
        this.userEventProducer = userEventProducer;
    }

    @Override
    public void processUserAddress(UserCollectedEvent event) {

        var responseDto = externalAddressService.fetchByUserId(event.getUserId());
        event.setAddress(responseDto.address());

        userEventProducer.sendCollectedEvent(event);
    }

}
