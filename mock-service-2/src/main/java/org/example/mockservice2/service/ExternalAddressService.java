package org.example.mockservice2.service;

import org.example.mockservice2.dto.UserAddressResponseDto;

public interface ExternalAddressService {

    UserAddressResponseDto fetchByUserId(Long id);

}
