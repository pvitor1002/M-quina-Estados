package org.example.maquinaestados.adapters.datastore;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.maquinaestados.domain.entities.MaquinaEstado;

import java.util.Optional;
import java.util.UUID;

public interface MaquinaEstadoDatastore {
    Optional<MaquinaEstado> get(UUID id) throws JsonProcessingException;
    boolean get(UUID id, String faseEvento);
    void put(MaquinaEstado maquinaEstado) throws JsonProcessingException;
}
