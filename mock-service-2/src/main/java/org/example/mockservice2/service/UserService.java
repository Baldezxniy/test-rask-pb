package org.example.mockservice2.service;


import org.example.event.UserCollectedEvent;

public interface UserService {

    void processUserAddress(UserCollectedEvent event);

}
