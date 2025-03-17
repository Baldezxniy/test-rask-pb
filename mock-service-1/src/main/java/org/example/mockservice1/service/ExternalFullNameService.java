package org.example.mockservice1.service;

import org.example.mockservice1.dto.UserFullNameResponseDto;

public interface ExternalFullNameService {

    UserFullNameResponseDto fetchByUserId(Long id);

}
