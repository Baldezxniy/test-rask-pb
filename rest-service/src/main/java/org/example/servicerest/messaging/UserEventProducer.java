package org.example.servicerest.messaging;

import org.example.event.UserCollectedEvent;

public interface UserEventProducer {

    void sendCollectedEvent(UserCollectedEvent event);

}
