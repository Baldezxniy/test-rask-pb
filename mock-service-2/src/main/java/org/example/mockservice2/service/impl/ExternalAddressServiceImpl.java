package org.example.mockservice2.service.impl;

import org.example.mockservice2.dto.UserAddressResponseDto;
import org.example.mockservice2.service.ExternalAddressService;
import org.example.mockservice2.utils.UserAddressGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ExternalAddressServiceImpl implements ExternalAddressService {

    private static final Random random = new Random();

    private static final Logger logger = LoggerFactory.getLogger(ExternalAddressServiceImpl.class);

    @Override
    @Retryable(
            retryFor = RuntimeException.class,
            backoff = @Backoff(delay = 100))
    public UserAddressResponseDto fetchByUserId(Long id) {
        logger.info("Fetching user data for user ID: {}", id);
        if (random.nextDouble() < 0.1) {
            logger.warn("External service wasn't available while fetching user data for user ID: {}", id);
            throw new RuntimeException();
        }

        var response = UserAddressGenerator.getByUserId(id);
        logger.info("User data successfully retrieved for user ID: {}", id);
        return response;
    }

    @Recover
    public void recoverFromFailure(RuntimeException e, Long id) {
        logger.error("All retry attempts failed for user ID: {}. Returning default data.", id);
    }
}
