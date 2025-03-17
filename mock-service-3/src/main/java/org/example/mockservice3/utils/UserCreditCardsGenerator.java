package org.example.mockservice3.utils;

import org.example.mockservice3.dto.UserCreditCardsResponseDto;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public final class UserCreditCardsGenerator {

    private static final SecureRandom random = new SecureRandom();

    private static final int[] NUMBERS = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};

    public static UserCreditCardsResponseDto getByUserId(Long id) {
        List<String> creditCards = new ArrayList<>();
        for (int i = 0; i < random.nextInt(2, 5); i++) {
            creditCards.add(generateCreditCard());
        }
        return new UserCreditCardsResponseDto(id, creditCards);
    }

    private static String generateCreditCard() {
        StringBuilder card = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            card.append(NUMBERS[random.nextInt(NUMBERS.length)]);
        }
        return card.toString();
    }
}
