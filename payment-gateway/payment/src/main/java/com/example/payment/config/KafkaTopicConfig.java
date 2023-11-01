package com.example.payment.config;

import com.example.payment.AppConstant;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Bean
    NewTopic paymentHealthProgramTopic() {
        return TopicBuilder
                .name(AppConstant.PAYMENT_HEALTH_PROGRAM)
                .build();
    }

    @Bean
    NewTopic paymentDetailTopic() {
        return TopicBuilder
                .name(AppConstant.PAYMENT_DETAIL)
                .build();
    }

    @Bean
    NewTopic cardNumberTopic() {
        return TopicBuilder
                .name(AppConstant.CARD_NUMBER)
                .build();
    }

    @Bean
    NewTopic cardDetailTopic() {
        return TopicBuilder
                .name(AppConstant.CARD_DETAIL)
                .build();
    }
}
