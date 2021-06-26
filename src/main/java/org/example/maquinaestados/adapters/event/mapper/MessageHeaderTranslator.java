package org.example.maquinaestados.adapters.event.mapper;

import org.springframework.messaging.MessageHeaders;

public interface MessageHeaderTranslator {

    void translate(MessageHeaders headers);
}
