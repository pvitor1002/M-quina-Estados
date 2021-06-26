package org.example.maquinaestados.application.services.handle;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.messaging.MessageHeaders;

import java.util.UUID;

public interface MaquinaCriadaHandle {
    void transferir(UUID id) throws JsonProcessingException;
}
