package org.example.maquinaestados.adapters.beans;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.example.maquinaestados.adapters.event.listener.TransferenciaErrorHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.listener.concurrency}")
    private String concurrency;

    @Value("${transferencia.kafka.schedule.group-id}")
    private String groupIdSchedule;

    @Value("${transferencia.kafka.schedule.concurrency}")
    private Integer concurrencySchedule;

    @Value("${spring.kafka.bootstrap-server}")
    private String bootstrapAddress;

    @Bean
    KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> transferenciaListenerContainerFactoryBean(
            ConsumerFactory<String, String> consumerFactory, TransferenciaErrorHandler errorHandler){
        final Map<String, Object> configurations = ((DefaultKafkaConsumerFactory<String, String>) consumerFactory)
                .getConfigurationProperties();
        final Map<String, Object> mapaConfiguration = new HashMap<>(configurations);

        DefaultKafkaConsumerFactory<String, String> consumerFactoryEdit = new DefaultKafkaConsumerFactory<>(mapaConfiguration);

        ConcurrentKafkaListenerContainerFactory<String, String> containerFactory = new ConcurrentKafkaListenerContainerFactory<>();
        containerFactory.setConsumerFactory(consumerFactoryEdit);
        //containerFactory.setErrorHandler(errorHandler);
        containerFactory.setConcurrency(Integer.parseInt(concurrency));
        containerFactory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        return containerFactory;
    }

    @Bean
    ConsumerFactory<String, String> consumerFactory(){
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "transferencia");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    KafkaTemplate<String, String> getKafkaTemplate(ProducerFactory<String, String> producerFactory){
        KafkaTemplate<String, String> template = new KafkaTemplate<>(producerFactory);
        template.setDefaultTopic("transferencia-finalizada");
        return template;
    }

    @Bean
    ProducerFactory<String, String> producerFactory(){
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(props);
    }

}
