package org.example.mockservice3.messaging;

import org.example.event.UserCollectedEvent;

public interface UserEventProducer {

    void sendCollectedEvent(UserCollectedEvent event);

}
