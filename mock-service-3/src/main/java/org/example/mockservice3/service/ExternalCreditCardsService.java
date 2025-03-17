package org.example.mockservice3.service;

import org.example.mockservice3.dto.UserCreditCardsResponseDto;

public interface ExternalCreditCardsService {

    UserCreditCardsResponseDto fetchByUserId(Long id);

}
