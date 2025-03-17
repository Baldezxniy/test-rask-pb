package org.example.mockservice1.dto;

public record UserFullNameResponseDto(
        Long id,
        String firstName,
        String lastName,
        String middleName
) {

}
