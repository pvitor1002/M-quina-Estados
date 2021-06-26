package org.example.maquinaestados.adapters.event.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.maquinaestados.adapters.event.mapper.MessageHeaderTranslator;
import org.example.maquinaestados.application.services.StateMachineService;
import org.example.maquinaestados.application.usecases.AtualizarMaquinaUsecase;
import org.example.maquinaestados.domain.entities.FaseEvento;
import org.example.maquinaestados.domain.entities.maquinaestado.EventoMudancaEstado;
import org.example.maquinaestados.domain.types.TipoEventoTransferencia;
import org.example.statemachine.StateMachine;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class StateChangeKafkaListener {

    private final AtualizarMaquinaUsecase atualizarMaquinaUsecase;
    private final MessageHeaderTranslator messageHeaderTranslator;

    @KafkaListener(topics = "${transferencia.kafka.consumer.topic-estado}", groupId = "estado", containerFactory = "estadoListenerContainerFactoryBean")
    public void onEvent(final ConsumerRecord<String, EventoMudancaEstado> record,
                        @Headers MessageHeaders messageHeaders, final Acknowledgment ack) throws JsonProcessingException {
        try {
            messageHeaderTranslator.translate(messageHeaders);
            atualizarMaquinaUsecase.execute(record.value());
        } finally {
            ack.acknowledge();
        }

    }
}
