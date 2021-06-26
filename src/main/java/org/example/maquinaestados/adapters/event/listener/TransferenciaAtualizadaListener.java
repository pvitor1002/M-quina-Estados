package org.example.maquinaestados.adapters.event.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.maquinaestados.adapters.event.entity.Response;
import org.example.maquinaestados.adapters.event.mapper.MessageHeaderTranslator;
import org.example.maquinaestados.adapters.event.producer.TransferenciaProducer;
import org.example.maquinaestados.domain.entities.maquinaestado.EventoMudancaEstado;
import org.example.maquinaestados.domain.types.TipoEventoTransferencia;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@KafkaListener(topics = "${transferencia.kafka.consumer.topic-atutalizado}", containerFactory = "transferenciaListenerContainerFactoryBean")
public class TransferenciaAtualizadaListener {

    private final TransferenciaProducer transferenciaProducer;
    private final MessageHeaderTranslator messageHeaderTranslator;

    @KafkaHandler
    public void transferenciaAtualizada(@Payload String payload, @Headers MessageHeaders messageHeaders, Acknowledgment ack) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        System.out.println("Iniciando transferencia.");
        messageHeaderTranslator.translate(messageHeaders);
        String payload1 = payload.replaceAll("\\\\","").replaceFirst("\"","");
        Response response = mapper.readValue(payload1.substring(0, payload1.length()-1), Response.class);

        EventoMudancaEstado<Object> eventoMudancaEstado = buildEventoMudancaEstado(response, messageHeaders);

        transferenciaProducer.produceInternalTopic(eventoMudancaEstado);
        ack.acknowledge();
    }

    private EventoMudancaEstado<Object> buildEventoMudancaEstado(Response value, MessageHeaders headers){
        return EventoMudancaEstado.builder()
                .eventoOriginal("")
                .tipoEventoTransferencia(TipoEventoTransferencia.valueOf(new String((byte[]) headers.get("type"), StandardCharsets.UTF_8)))
                .id(UUID.fromString(value.getId()))
                .build();
    }
}
