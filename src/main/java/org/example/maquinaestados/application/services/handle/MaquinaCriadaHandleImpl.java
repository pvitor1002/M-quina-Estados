package org.example.maquinaestados.application.services.handle;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.example.maquinaestados.adapters.event.entity.Request;
import org.example.maquinaestados.adapters.event.producer.TransferenciaProducer;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MaquinaCriadaHandleImpl implements MaquinaCriadaHandle{

    private final TransferenciaProducer transferenciaProducer;

    @Override
    public void transferir(UUID id) throws JsonProcessingException {
        Request request = Request.builder()
                .id(id.toString())
                .messageIndex(1)
                .origin(1L)
                .senha("123")
                .build();

        transferenciaProducer.produce(request);
    }
}
