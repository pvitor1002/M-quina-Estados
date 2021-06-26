package org.example.maquinaestados.adapters.event.mapper;

import lombok.RequiredArgsConstructor;
import org.example.maquinaestados.adapters.datastore.ContextDatastore;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class MessageHeaderTranslatorImpl implements MessageHeaderTranslator{

    private final ContextDatastore contextDatastore;

    @Override
    public void translate(MessageHeaders messageHeaders){

        final Map<String, Object> headers = new HashMap<>();

        messageHeaders.forEach((k, v) -> {
            try {
                headers.put(k, new String((byte[]) v, StandardCharsets.UTF_8));
            }catch(Exception e){
                System.err.println("Objeto inválido");
            }
        });
        contextDatastore.setHeaders(headers);
    }
}
