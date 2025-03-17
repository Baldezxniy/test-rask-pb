package org.example.mockservice1.service;

import org.example.event.UserCollectedEvent;
import org.example.mockservice1.dto.UserFullNameResponseDto;
import org.example.mockservice1.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class UserServiceIntegrationTest {

    private static final Long TEST_USER_ID = 123L;
    private static final String TEST_FIRST_NAME = "firstName";
    private static final String TEST_LAST_NAME = "lastName";
    private static final String TEST_MIDDLE_NAME = "middleName";

    private UserCollectedEvent event;

    @InjectMocks
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private JmsTemplate jmsTemplate;

    @MockBean
    private ExternalFullNameService externalFullNameService;

    @BeforeEach
    void setUp() {
        event = new UserCollectedEvent();
        event.setUserId(TEST_USER_ID);
    }

    @Test
    void shouldSendMessageToInMemoryBrokerQueue() {
        // Arrange
        var mockResponse = new UserFullNameResponseDto(
                TEST_USER_ID, TEST_FIRST_NAME, TEST_LAST_NAME, TEST_MIDDLE_NAME);

        Mockito.when(externalFullNameService.fetchByUserId(TEST_USER_ID))
                .thenReturn(mockResponse);

        jmsTemplate.convertAndSend("users.collect-full-name.queue", event);
        jmsTemplate.setReceiveTimeout(3000);

        // Act
        Object received = jmsTemplate.receiveAndConvert("users.collect-address.queue");

        assertThat(received)
                .isNotNull()
                .isInstanceOf(UserCollectedEvent.class);

        // Assets
        UserCollectedEvent updatedEvent = (UserCollectedEvent) received;
        assertThat(updatedEvent.getUserId()).isEqualTo(TEST_USER_ID);
        assertThat(updatedEvent.getFirstName()).isEqualTo(TEST_FIRST_NAME);
        assertThat(updatedEvent.getLastName()).isEqualTo(TEST_LAST_NAME);
        assertThat(updatedEvent.getMiddleName()).isEqualTo(TEST_MIDDLE_NAME);
    }

}