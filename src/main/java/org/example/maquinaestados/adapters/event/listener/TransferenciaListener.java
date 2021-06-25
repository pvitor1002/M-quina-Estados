package org.example.maquinaestados.adapters.event.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.maquinaestados.adapters.event.entity.Request;
import org.example.maquinaestados.adapters.event.entity.Response;
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

import java.util.Base64;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@KafkaListener(topics = "${transferencia.kafka.consumer.topic-comando}", containerFactory = "transferenciaListenerContainerFactoryBean")
public class TransferenciaListener {

    private final TransferenciaProducer transferenciaProducer;

    @KafkaHandler
    public void transferenciaSolicitada(@Payload String payload, @Headers MessageHeaders messageHeaders, Acknowledgment ack) throws JsonProcessingException {

        try {
            ObjectMapper mapper = new ObjectMapper();
            String transferencia64 = payload.replaceAll("\"", "");
            byte[] decoded = Base64.getDecoder().decode(transferencia64);
            System.out.println("Iniciando transferencia.");
            Request request = mapper.readValue(decoded, Request.class);

            Response response = new Response();
            response.setId(request.getId());
            response.setMessage("Message " + request.getMessageIndex());
            response.setOrigin(request.getOrigin());

            System.out.println("Objeto convertido: " + response);

            EventoMudancaEstado<Object> eventoMudancaEstado = EventoMudancaEstado.builder()
                    .eventoOriginal("")
                    .tipoEventoTransferencia(TipoEventoTransferencia.CRIAR_MAQUINA_ESTADOS)
                    .id(UUID.fromString(request.getId()))
                    .build();
            transferenciaProducer.produceInternalTopic(eventoMudancaEstado);
            //transferenciaProducer.produce(response, messageHeaders);
        } catch (Exception e){
            System.err.println("Erro desconhecido");
        } finally {
            ack.acknowledge();
        }
    }
}
