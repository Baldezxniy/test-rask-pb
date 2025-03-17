package org.example.dataservice.service;

import org.example.dataservice.entity.User;
import org.example.dataservice.mapper.UserMapper;
import org.example.dataservice.repository.UserRespository;
import org.example.event.UserCollectedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRespository userRespository;

    public UserServiceImpl(UserRespository userRespository) {
        this.userRespository = userRespository;
    }

    @Override
    @Transactional
    public void createUserFromEvent(UserCollectedEvent event) {
        logger.info("Mapping event to user entity with ID: {}", event.getUserId());
        User entity = UserMapper.toEntity(event);
        User savedUser = userRespository.save(entity);
        logger.info("User successfully saved to database with ID: {}", savedUser.getId());
    }

}
