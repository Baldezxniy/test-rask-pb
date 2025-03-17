package org.example.mockservice2.messaging;

import org.example.event.UserCollectedEvent;

public interface UserEventProducer {

    void sendCollectedEvent(UserCollectedEvent event);

}
