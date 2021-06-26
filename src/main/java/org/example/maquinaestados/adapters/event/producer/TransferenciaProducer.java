package org.example.maquinaestados.adapters.event.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.maquinaestados.adapters.event.entity.Request;
import org.example.maquinaestados.adapters.event.entity.Response;
import org.example.maquinaestados.domain.entities.maquinaestado.EventoMudancaEstado;
import org.springframework.messaging.MessageHeaders;

public interface TransferenciaProducer {

    void produce(Response response) throws JsonProcessingException;
    void produce(Request request) throws JsonProcessingException;
    void produceInternalTopic(EventoMudancaEstado evento);
}
