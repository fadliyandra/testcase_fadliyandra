package com.example.bills.config.consumer;


import com.example.bills.config.KafkaConsumerConfig;
import com.example.dto.PaymentDetail;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Map;


@EnableKafka
@Configuration
@Slf4j
public class PaymentDetailConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;

    @Bean("PaymentDetailConsumerConfig")
    ConsumerFactory<String, PaymentDetail> consumerConfig(KafkaConsumerConfig config) {
        Map<String, Object> props = config.propertiesConfig();

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                new JsonDeserializer<>(Object.class)
        );
    }

    @Bean("PaymentDetailFactory")
    ConcurrentKafkaListenerContainerFactory<String, PaymentDetail> paymentDetailListenerContainerFactory(
            @Qualifier("PaymentDetailConsumerConfig") ConsumerFactory<String, PaymentDetail> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, PaymentDetail> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }
}
