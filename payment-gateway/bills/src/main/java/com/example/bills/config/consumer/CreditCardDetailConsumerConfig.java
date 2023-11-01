package com.example.bills.config.consumer;

import com.example.bills.config.KafkaConsumerConfig;
import com.example.dto.CreditCardDetail;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Map;

@EnableKafka
@Configuration
@Slf4j
public class CreditCardDetailConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;

    @Bean("CreditCardDetailConsumerConfig")
    ConsumerFactory<String, CreditCardDetail> consumerConfig(KafkaConsumerConfig config) {
        Map<String, Object> props = config.propertiesConfig();

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                new JsonDeserializer<>(CreditCardDetail.class)
        );
    }

    @Bean("CreditCardDetailFactory")
    ConcurrentKafkaListenerContainerFactory<String, CreditCardDetail> creditCardDetailListenerContainerFactory(
            @Qualifier("CreditCardDetailConsumerConfig") ConsumerFactory<String, CreditCardDetail> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, CreditCardDetail> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }
}
