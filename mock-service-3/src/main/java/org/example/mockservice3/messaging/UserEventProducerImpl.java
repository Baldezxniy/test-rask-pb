package org.example.mockservice3.messaging;

import org.example.event.UserCollectedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserEventProducerImpl implements UserEventProducer {

    private static final Logger logger = LoggerFactory.getLogger(UserEventProducerImpl.class);

    private static final String QUEUE_NAME = "users.collect-storage.queue";

    private final JmsTemplate jmsTemplate;

    public UserEventProducerImpl(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void sendCollectedEvent(UserCollectedEvent event) {
        try {
            logger.info("Attempting send event to to queue '{}': {}", QUEUE_NAME, event);
            jmsTemplate.convertAndSend(QUEUE_NAME, event, message -> {
                message.setStringProperty("_type", event.getClass().getName());
                return message;
            });
            logger.info("Event successfully sent to queue '{}': {}", QUEUE_NAME, event);
        } catch (Exception e) {
            logger.error("Received exception during send Message: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
