package org.example.mockservice3.service;


import org.example.event.UserCollectedEvent;

public interface UserService {

    void processUserCreditCarts(UserCollectedEvent event);

}
