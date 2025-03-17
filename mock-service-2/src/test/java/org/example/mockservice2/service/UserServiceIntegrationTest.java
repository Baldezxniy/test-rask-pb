package org.example.mockservice2.service;

import org.example.event.UserCollectedEvent;
import org.example.mockservice2.dto.UserAddressResponseDto;
import org.example.mockservice2.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class UserServiceIntegrationTest {

    private static final Long TEST_USER_ID = 123L;
    private static final String TEST_ADDRESS = "address";

    private UserCollectedEvent event;

    @InjectMocks
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private JmsTemplate jmsTemplate;

    @MockBean
    private ExternalAddressService externalAddressService;

    @BeforeEach
    void setUp() {
        event = new UserCollectedEvent();
        event.setUserId(TEST_USER_ID);
        event.setFirstName("firstName");
        event.setLastName("lastName");
        event.setMiddleName("middleName");
    }

    @Test
    void shouldSendMessageToInMemoryBrokerQueue() {
        // Arrange
        var mockResponse = new UserAddressResponseDto(TEST_USER_ID, TEST_ADDRESS);

        Mockito.when(externalAddressService.fetchByUserId(TEST_USER_ID))
                .thenReturn(mockResponse);

        jmsTemplate.convertAndSend("users.collect-address.queue", event);
        jmsTemplate.setReceiveTimeout(3000);

        // Act
        Object received = jmsTemplate.receiveAndConvert("users.collect-credit-card.queue");

        assertThat(received)
                .isNotNull()
                .isInstanceOf(UserCollectedEvent.class);

        // Assets
        UserCollectedEvent updatedEvent = (UserCollectedEvent) received;
        assertThat(updatedEvent.getUserId()).isEqualTo(TEST_USER_ID);
        assertThat(updatedEvent.getAddress()).isEqualTo(TEST_ADDRESS);
    }

}