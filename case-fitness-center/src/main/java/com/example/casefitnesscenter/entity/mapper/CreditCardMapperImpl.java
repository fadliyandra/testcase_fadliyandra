package com.example.casefitnesscenter.entity.mapper;


import com.example.casefitnesscenter.entity.dto.MembershipDetailsRequest;
import com.example.casefitnesscenter.entity.model.CreditCard;
import com.example.casefitnesscenter.entity.model.Member;
import com.example.casefitnesscenter.utils.DateConverter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

import static com.example.casefitnesscenter.utils.DateConverter.MONTH_YEARS_FORMAT;
import static com.example.casefitnesscenter.utils.DateConverter.parseToDate;


@Component
public class CreditCardMapperImpl implements CreditCardMapper {
    @Override
    public CreditCard toEntity(MembershipDetailsRequest.CreditCardDto creditCardDto, Member member) {
        Date date = parseToDate(creditCardDto.expiredDate(), MONTH_YEARS_FORMAT);

        CreditCard creditCard = new CreditCard();
        creditCard.setCreatedAt(new Date(System.currentTimeMillis()));
        creditCard.setPlaceholder(creditCardDto.placeholder());
        creditCard.setCardNumber(creditCardDto.cardNumber());
        creditCard.setExpiredDate(date);
        creditCard.setCvv(Integer.parseInt(creditCardDto.cvv()));
        creditCard.setMember(member);

        return creditCard;
    }

    @Override
    public MembershipDetailsRequest.CreditCardDto toDto(CreditCard creditCard) {
        LocalDateTime dateTime = DateConverter.toLocalDateTime(creditCard.getExpiredDate());
        String timeFormatter = DateConverter.formatDate(MONTH_YEARS_FORMAT, dateTime);

        return MembershipDetailsRequest.CreditCardDto.builder()
                .placeholder(creditCard.getPlaceholder())
                .cardNumber(creditCard.getCardNumber())
                .expiredDate(timeFormatter)
                .cvv(String.valueOf(creditCard.getCvv()))
                .build();
    }
}
