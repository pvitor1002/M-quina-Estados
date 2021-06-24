package org.example.maquinaestados.adapters.event.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.maquinaestados.adapters.event.entity.Response;
import org.springframework.messaging.MessageHeaders;

public interface TransferenciaProducer {

    public void produce(Response response, MessageHeaders messageHeaders) throws JsonProcessingException;
}
