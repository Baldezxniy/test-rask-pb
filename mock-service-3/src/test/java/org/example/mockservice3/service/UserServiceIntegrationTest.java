package org.example.mockservice3.service;

import org.example.event.UserCollectedEvent;
import org.example.mockservice3.dto.UserCreditCardsResponseDto;
import org.example.mockservice3.service.impl.UserServiceImpl;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class UserServiceIntegrationTest {

    private static final Long TEST_USER_ID = 123L;
    private static final List<String> TEST_CREDIT_CARDS = List.of("4365874856597967", "4365875489281242");

    private UserCollectedEvent event;

    @InjectMocks
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private JmsTemplate jmsTemplate;

    @MockBean
    private ExternalCreditCardsService externalCreditCardsService;

    @BeforeEach
    void setUp() {
        event = new UserCollectedEvent();
        event.setUserId(TEST_USER_ID);
        event.setFirstName("firstName");
        event.setLastName("lastName");
        event.setMiddleName("middleName");
        event.setAddress("address");
    }

    @Test
    void shouldSendMessageToInMemoryBrokerQueue() {
        // Arrange
        var mockResponse = new UserCreditCardsResponseDto(TEST_USER_ID, TEST_CREDIT_CARDS);

        Mockito.when(externalCreditCardsService.fetchByUserId(TEST_USER_ID))
                .thenReturn(mockResponse);

        jmsTemplate.convertAndSend("users.collect-credit-card.queue", event);
        jmsTemplate.setReceiveTimeout(3000);

        // Act
        Object received = jmsTemplate.receiveAndConvert("users.collect-storage.queue");

        assertThat(received)
                .isNotNull()
                .isInstanceOf(UserCollectedEvent.class);

        // Assets
        UserCollectedEvent updatedEvent = (UserCollectedEvent) received;
        assertThat(updatedEvent.getUserId()).isEqualTo(TEST_USER_ID);
        assertThat(updatedEvent.getCreditCardNumbers()).containsAll(TEST_CREDIT_CARDS);
    }

}