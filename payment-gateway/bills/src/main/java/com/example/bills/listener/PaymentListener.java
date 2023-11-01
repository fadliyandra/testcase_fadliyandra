package com.example.bills.listener;


import com.example.bills.AppConstant;
import com.example.dto.CreditCardDetail;
import com.example.dto.CreditCardNumber;
import com.example.dto.KafkaPaymentResponse;
import com.example.dto.PaymentDetail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.event.ListenerContainerIdleEvent;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.concurrent.CountDownLatch;

@Component
@Slf4j
@RequiredArgsConstructor
public class PaymentListener implements ConsumerSeekAware {

    private final SimpMessagingTemplate messagingTemplate;
    private final KafkaListenerEndpointRegistry endpointRegistry;
    private CountDownLatch latch;

    @EventListener
    public void onIdleEvent(ListenerContainerIdleEvent event) {
        System.out.println(event);
        latch.countDown();
    }

    @KafkaListener(
            id = "ccr",
            topics = AppConstant.CARD_DETAIL,
            groupId = AppConstant.GROUP_PAYMENT_ID,
            containerFactory = "CreditCardDetailFactory"
    )
    public void listenerCreditCardDetail(ConsumerRecord<String, CreditCardDetail> recordCCD) {
        System.out.println("Listener Received: " + recordCCD.value());
        System.out.println("Received at " + parseToDate(recordCCD.timestamp()));
        KafkaPaymentResponse paymentResponse = this.createPayload(recordCCD.timestamp());
        messagingTemplate.convertAndSend("/program-health/payment", paymentResponse);
    }

    @KafkaListener(
            id = "pd",
            topics = AppConstant.PAYMENT_DETAIL,
            groupId = AppConstant.GROUP_PAYMENT_ID,
            containerFactory = "PaymentDetailFactory"
    )
    public void listenerPaymentDetail(ConsumerRecord<String, PaymentDetail> recordPD) {
        System.out.println("Listener Received: " + recordPD.value());
        System.out.println("Received at " + parseToDate(recordPD.timestamp()));
        KafkaPaymentResponse paymentResponse = this.createPayload(recordPD.timestamp());
        messagingTemplate.convertAndSend("/program-health/payment", paymentResponse);
    }

    @KafkaListener(
            id = "ccn",
            topics = AppConstant.CARD_NUMBER,
            groupId = AppConstant.GROUP_PAYMENT_ID,
            containerFactory = "CreditCardNumberFactory"
    )
    public void listenerCreditCardNumber(ConsumerRecord<String, CreditCardNumber> recordCCN) {
        System.out.println("Listener Received: " + recordCCN.value());
        System.out.println("Received at " + parseToDate(recordCCN.timestamp()));
        KafkaPaymentResponse paymentResponse = this.createPayload(recordCCN.timestamp());
        messagingTemplate.convertAndSend("/program-health/payment", paymentResponse);
    }

    @Scheduled(initialDelay = 1000, fixedDelay = 3000)
    void consumption() {
        this.endpointRegistry


                .getListenerContainerIds()
                .forEach(s -> {
                    System.out.println("container ID:" + s);
                    latch = new CountDownLatch(1);
                    this.endpointRegistry.getListenerContainer(s).start();
                    try {
                        latch.await();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    this.endpointRegistry.getListenerContainer(s).stop();
                });
    }

    private KafkaPaymentResponse createPayload(long epochSeconds) {
        String date = parseToDate(epochSeconds);

        return KafkaPaymentResponse.builder()
                .timestamp(date)
                .status("SUCCESS")
                .build();
    }

    private String parseToDate(long epochSeconds) {
        return Instant
                .ofEpochMilli(epochSeconds)
                .toString();
    }
}
