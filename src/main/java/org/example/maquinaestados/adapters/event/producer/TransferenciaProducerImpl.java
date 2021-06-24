package org.example.maquinaestados.adapters.event.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.example.maquinaestados.adapters.event.entity.Response;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TransferenciaProducerImpl implements TransferenciaProducer{

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void produce(Response response, MessageHeaders messageHeaders) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        String event = mapper.writeValueAsString(response);
        Message<String> message = MessageBuilder.withPayload(event).copyHeaders(messageHeaders).build();

        kafkaTemplate.send(message).addCallback(
                successCallback -> {
                    System.out.println("Mensagem enviada com sucesso!");
                },
                failureCallback -> {
                    System.err.println("Erro ao enviar mensagem!");
                });
    }
}
