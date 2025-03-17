package org.example.mockservice1.messaging;

import org.example.event.UserCollectedEvent;

public interface UserEventProducer {

    void sendCollectedEvent(UserCollectedEvent event);

}
