package org.example.dataservice.mapper;

import org.example.dataservice.entity.CreditCard;
import org.example.dataservice.entity.User;
import org.example.event.UserCollectedEvent;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public final class UserMapper {

    public static User toEntity(UserCollectedEvent event) {
        User userEntity = new User();
        BeanUtils.copyProperties(event, userEntity);
        userEntity.setId(event.getUserId());

        List<CreditCard> creditCards = event.getCreditCardNumbers().stream()
                .map(UserMapper::toCreditCard)
                .peek(creditCard -> creditCard.setUser(userEntity))
                .collect(Collectors.toList());
        userEntity.setCreditCards(creditCards);

        return userEntity;
    }

    private static CreditCard toCreditCard(String creditCard) {
        CreditCard creditCardEntity = new CreditCard();
        creditCardEntity.setCreditCardNumber(creditCard);
        return creditCardEntity;
    }
}
