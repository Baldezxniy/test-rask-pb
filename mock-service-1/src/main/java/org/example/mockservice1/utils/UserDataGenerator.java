package org.example.mockservice1.utils;

import org.example.mockservice1.dto.UserFullNameResponseDto;

import java.security.SecureRandom;

public final class UserDataGenerator {


    private static final SecureRandom random = new SecureRandom();

    private static final String[] FIRST_NAMES = {
            "Олександр", "Андрій", "Максим", "Дмитро", "Іван",
            "Володимир", "Сергій", "Юрій", "Микола", "Василь"};

    private static final String[] LAST_NAMES = {
            "Шевченко", "Коваленко", "Мельник", "Бондаренко", "Ткаченко",
            "Іваненко", "Григоренко", "Сидоренко", "Петренко", "Лисенко"};

    private static final String[] MIDDLE_NAMES = {
            "Олександрович", "Андрійович", "Максимович", "Дмитрович", "Іванович",
            "Володимирович", "Сергійович", "Юрійович", "Миколайович", "Васильович"
    };

    public static UserFullNameResponseDto getByUserId(Long userId) {
        String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
        String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
        String middleName = MIDDLE_NAMES[random.nextInt(LAST_NAMES.length)];

        return new UserFullNameResponseDto(userId, firstName, lastName, middleName);
    }

}
