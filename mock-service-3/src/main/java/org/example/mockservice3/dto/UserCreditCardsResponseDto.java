package org.example.mockservice3.dto;

import java.util.List;

public record UserCreditCardsResponseDto(
        Long userId,
        List<String> creditCards
) {
}
