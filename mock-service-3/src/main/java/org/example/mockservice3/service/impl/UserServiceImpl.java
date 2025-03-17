package org.example.mockservice3.service.impl;

import org.example.event.UserCollectedEvent;
import org.example.mockservice3.messaging.UserEventProducer;
import org.example.mockservice3.service.ExternalCreditCardsService;
import org.example.mockservice3.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final ExternalCreditCardsService externalCreditCardsService;
    private final UserEventProducer userEventProducer;

    public UserServiceImpl(
            ExternalCreditCardsService externalCreditCardsService,
            UserEventProducer userEventProducer) {
        this.externalCreditCardsService = externalCreditCardsService;
        this.userEventProducer = userEventProducer;
    }

    @Override
    public void processUserCreditCarts(UserCollectedEvent event) {

        var responseDto = externalCreditCardsService.fetchByUserId(event.getUserId());
        event.setCreditCardNumbers(responseDto.creditCards());
        userEventProducer.sendCollectedEvent(event);
    }

}
