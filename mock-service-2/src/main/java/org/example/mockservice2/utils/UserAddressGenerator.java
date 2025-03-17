package org.example.mockservice2.utils;

import org.example.mockservice2.dto.UserAddressResponseDto;

import java.security.SecureRandom;

public final class UserAddressGenerator {

    private static final SecureRandom random = new SecureRandom();

    private static final String[] CITIES = {
            "Київ", "Львів", "Одеса", "Дніпро", "Харків",
            "Вінниця", "Полтава", "Житомир", "Черкаси", "Тернопіль"
    };

    private static final String[] STREETS = {
            "Шевченка", "Франка", "Грушевського", "Бандери", "Лесі Українки",
            "Сагайдачного", "Коновальця", "Богдана Хмельницького", "Пушкіна", "Коцюбинського"
    };

    public static UserAddressResponseDto getByUserId(Long userId) {
        StringBuilder address = new StringBuilder("м. ")
                .append(CITIES[random.nextInt(CITIES.length)])
                .append(", в. ")
                .append(STREETS[random.nextInt(STREETS.length)])
                .append(", буд. ")
                .append(random.nextInt(200) + 1);

        return new UserAddressResponseDto(userId, address.toString());
    }

}