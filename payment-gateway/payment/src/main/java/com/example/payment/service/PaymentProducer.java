package com.example.payment.service;

import com.example.dto.CreditCardDetail;
import com.example.dto.CreditCardNumber;
import com.example.dto.PaymentDetail;
import com.example.dto.PaymentHealthProgram;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import static com.example.payment.AppConstant.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(String creditCard) {
        log.info("Credit card will encrypted: {}", creditCard);
        PaymentHealthProgram decryptPaymentHealthProgram = decryptCreditCard(creditCard);
        String placeholder = decryptPaymentHealthProgram.placeholder();
        String cardNumber = decryptPaymentHealthProgram.cardNumber();
        Integer cvv = decryptPaymentHealthProgram.cvv();
        Double totalPay = decryptPaymentHealthProgram.totalPay();

        kafkaTemplate.send(PAYMENT_HEALTH_PROGRAM, decryptPaymentHealthProgram);
        log.info("Credit card successfully send to kafka: {}", decryptPaymentHealthProgram);

        PaymentDetail paymentDetail = PaymentDetail.builder()
                .totalPay(totalPay)
                .cardNumber(cardNumber)
                .build();
        kafkaTemplate.send(PAYMENT_DETAIL, paymentDetail);
        log.info("Payment detail send to kafka: {}", paymentDetail.toString());

        CreditCardNumber creditCardNumber = CreditCardNumber.builder()
                .cardNumber(cardNumber)
                .cvv(cvv)
                .build();
        kafkaTemplate.send(CARD_NUMBER, creditCardNumber);
        log.info("Credit card Number send to kafka: {}", creditCardNumber.toString());

        CreditCardDetail creditCardDetail = CreditCardDetail.builder()
                .cardNumber(cardNumber)
                .placeholder(placeholder)
                .build();
        kafkaTemplate.send(CARD_DETAIL, creditCardDetail);
        log.info("Credit card detail send to kafka: {}", creditCardDetail.toString());
    }

    private PaymentHealthProgram decryptCreditCard(String encryptedCreditCard) {
        String strCC = this.decryptFromAES(encryptedCreditCard);
        String[] split = strCC.split("\\|");
        if (split.length > 4) {
            String expiredDate = split[3];
            Date date = this.parseToDate(expiredDate, "yyyy-MM-dd HH:mm:ss.S");

            return PaymentHealthProgram.builder()
                    .totalPay(Double.parseDouble(split[0]))
                    .placeholder(split[1])
                    .cardNumber(split[2])
                    .expiredDate(date)
                    .cvv(Integer.parseInt(split[4]))
                    .build();
        }

        String expiredDate = split[2];
        Date date = this.parseToDate(expiredDate, "yyyy-MM-dd HH:mm:ss.S");

        return PaymentHealthProgram.builder()
                .placeholder(split[0])
                .cardNumber(split[1])
                .expiredDate(date)
                .cvv(Integer.parseInt(split[3]))
                .build();
    }

    private String decryptFromAES(String encryptedText) {
        final byte[] fixedKey = "1234567890123456".getBytes(StandardCharsets.UTF_8);
        final byte[] fixedIv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        try {
            byte[] decodedData = Base64.getDecoder().decode(encryptedText);

            SecretKey secretKey = new SecretKeySpec(fixedKey, "AES");
            GCMParameterSpec parameterSpec = new GCMParameterSpec(96, fixedIv);

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, parameterSpec);

            byte[] decryptedData = cipher.doFinal(decodedData);
            return new String(decryptedData, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public Date parseToDate(String date, String formatDate) {
        try {
            Date deliveryDate = null;
            var dateFormat = new SimpleDateFormat(formatDate);
            var parse = dateFormat.parse(date);
            long time = parse.getTime();
            if (time != 0) {
                deliveryDate = new Date(time);
            }

            return deliveryDate;
        } catch (ParseException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

}
