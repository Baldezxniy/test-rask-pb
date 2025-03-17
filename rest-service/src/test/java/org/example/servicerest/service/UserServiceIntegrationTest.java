package org.example.servicerest.service;

import org.example.event.UserCollectedEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Test
    void shouldSendMessageToInMemoryBrokerQueue() {
        // Arrange
        final Long userId = 123L;

        // Act
        userService.initCollectProcess(userId);
        jmsTemplate.setReceiveTimeout(3000);
        Object received = jmsTemplate.receiveAndConvert("users.collect-full-name.queue");

        // Assets
        assertThat(received)
                .isNotNull()
                .isInstanceOf(UserCollectedEvent.class);

        UserCollectedEvent event = (UserCollectedEvent) received;
        assertThat(event.getUserId())
                .isEqualTo(userId);
    }
}