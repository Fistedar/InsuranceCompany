package org.javaguru.travel.insurance.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
@EnableKafka
@Slf4j
@Profile("mysql-container")
public class KafkaConfiguration {

    @Bean
    @Primary
    public DefaultErrorHandler kafkaErrorHandler(
            KafkaOperations<String, String> dlqTemplate,
            @Value("${kafka.dlq.topic}") String dlqTopic) {

        FixedBackOff backOff = new FixedBackOff(1000L, 3);

        DefaultErrorHandler handler = new DefaultErrorHandler(
                (record, ex) -> {

                    String key = record.key() != null
                            ? record.key().toString()
                            : "null-key";

                    String value = record.value() != null
                            ? record.value().toString()
                            : "null-key";

                    log.error("Processing failed after 3 attempts for key: {}", key, ex);
                    log.info("Sending message to DLQ: {}", dlqTopic);

                    dlqTemplate.send(dlqTopic, key, value);
                },
                backOff);

        handler.setCommitRecovered(true);
        handler.addNotRetryableExceptions(JsonProcessingException.class);

        log.info("Configured Kafka error handler with DLQ topic: {}", dlqTopic);
        return handler;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(
            ConsumerFactory<String, String> consumerFactory,
            DefaultErrorHandler errorHandler) {

        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setCommonErrorHandler(errorHandler);

        log.debug("Configured Kafka listener container factory");
        return factory;
    }
}