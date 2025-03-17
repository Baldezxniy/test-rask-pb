package org.example.dataservice.service;

import org.example.dataservice.entity.User;
import org.example.dataservice.repository.UserRespository;
import org.example.event.UserCollectedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class UserServiceIntegrationTest {

    private static final Long TEST_USER_ID = 123L;

    private UserCollectedEvent event;

    @Autowired
    private UserRespository userRespository;

    @Autowired
    private JmsTemplate jmsTemplate;

    @BeforeEach
    void setUp() {
        event = new UserCollectedEvent();
        event.setUserId(TEST_USER_ID);
        event.setFirstName("firstName");
        event.setLastName("lastName");
        event.setMiddleName("middleName");
        event.setAddress("address");
        event.setCreditCardNumbers(List.of("1234123412341234", "4321432143214321"));
    }

    @Test
    void shouldSendMessageToInMemoryBrokerQueue() throws InterruptedException {
        // Arrange
        jmsTemplate.convertAndSend("users.collect-storage.queue", event);
        jmsTemplate.setReceiveTimeout(3000);
        Thread.sleep(5000);
        User savedUser = userRespository.findById(event.getUserId()).get();

        // Act & Assets
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isEqualTo(event.getUserId());
        assertThat(savedUser.getFirstName()).isEqualTo(event.getFirstName());
    }

}