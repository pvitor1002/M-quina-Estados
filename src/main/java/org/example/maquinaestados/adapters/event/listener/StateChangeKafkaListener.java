package org.example.maquinaestados.adapters.event.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.maquinaestados.application.services.StateMachineService;
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

    private final StateMachineService stateMachineService;

    @KafkaListener(topics = "${transferencia.kafka.consumer.topic-estado}", groupId = "estado", containerFactory = "estadoListenerContainerFactoryBean")
    public void onEvent(final ConsumerRecord<String, EventoMudancaEstado> record,
                        @Headers MessageHeaders messageHeaders, final Acknowledgment ack) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        EventoMudancaEstado eventoMudancaEstado = record.value();
        //Map<String, String> eventoCriarMaquina = (Map) record.value().getEventoOriginal();
        if(TipoEventoTransferencia.CRIAR_MAQUINA_ESTADOS.equals(eventoMudancaEstado.getType())){
            stateMachineService.createStateMachine(eventoMudancaEstado.getId());
        } else {
            StateMachine<FaseEvento, TipoEventoTransferencia> stateMachine = stateMachineService
                    .getStateMachine(eventoMudancaEstado.getId());
            if(stateMachine.sendEvent(eventoMudancaEstado)){
                stateMachineService.salvarStateMachine(eventoMudancaEstado.getId(), stateMachine);
            }
        }
        ack.acknowledge();
    }
}
