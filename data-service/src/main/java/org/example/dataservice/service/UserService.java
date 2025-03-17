package org.example.dataservice.service;

import org.example.event.UserCollectedEvent;

public interface UserService {

    void createUserFromEvent(UserCollectedEvent event);

}
