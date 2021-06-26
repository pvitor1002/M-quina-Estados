package org.example.maquinaestados.adapters.event.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.example.maquinaestados.adapters.datastore.ContextDatastore;
import org.example.maquinaestados.adapters.event.entity.Request;
import org.example.maquinaestados.adapters.event.entity.Response;
import org.example.maquinaestados.domain.entities.maquinaestado.EventoMudancaEstado;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TransferenciaProducerImpl implements TransferenciaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaTemplate<String, EventoMudancaEstado> kafkaTemplateEstado;
    private final ContextDatastore contextDatastore;

    @Override
    public void produce(Response response) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> headers = new HashMap<>();
        headers.put("replyChannel", contextDatastore.getHeaders().get("replyChannel").toString());
        headers.put("errorChannel", contextDatastore.getHeaders().get("errorChannel").toString());
        headers.put("instanceId", contextDatastore.getHeaders().get("instanceId").toString());
        String event = mapper.writeValueAsString(response);
        Message<String> message = MessageBuilder.withPayload(event).copyHeaders(new MessageHeaders(headers)).build();

        kafkaTemplate.send(message).addCallback(
                successCallback -> {
                    System.out.println("Mensagem enviada com sucesso!");
                },
                failureCallback -> {
                    System.err.println("Erro ao enviar mensagem!");
                });
    }

    @Override
    public void produce(Request request) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        String event = mapper.writeValueAsString(request);
        ProducerRecord<String, String> message = new ProducerRecord<>("transferencia-processada", UUID.randomUUID().toString(), event);

        message.headers().add("replyChannel", contextDatastore.getHeaders().get("replyChannel").toString().getBytes());
        message.headers().add("errorChannel", contextDatastore.getHeaders().get("errorChannel").toString().getBytes());
        message.headers().add("instanceId", contextDatastore.getHeaders().get("instanceId").toString().getBytes());
        kafkaTemplate.send(message).addCallback(
                successCallback -> {
                    System.out.println("Mensagem enviada com sucesso!");
                },
                failureCallback -> {
                    System.err.println("Erro ao enviar mensagem!");
                });
    }

    @Override
    public void produceInternalTopic(EventoMudancaEstado evento) {
        final ProducerRecord<String, EventoMudancaEstado> producerRecord = new ProducerRecord<>("comando-estado", UUID.randomUUID().toString(), evento);

        producerRecord.headers().add("replyChannel", contextDatastore.getHeaders().get("replyChannel").toString().getBytes());
        producerRecord.headers().add("errorChannel", contextDatastore.getHeaders().get("errorChannel").toString().getBytes());
        producerRecord.headers().add("instanceId", contextDatastore.getHeaders().get("instanceId").toString().getBytes());
        kafkaTemplateEstado.send(producerRecord).addCallback(s -> {
                    System.out.println("Enviado para tópico interno.");
                },
                f -> {
                    System.err.println("Erro ao enviar mensagem.");
                });
    }
}
