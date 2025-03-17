package org.example.mockservice1.service;

import org.example.event.UserCollectedEvent;

public interface UserService {

    void processUserFullName(UserCollectedEvent event);

}
