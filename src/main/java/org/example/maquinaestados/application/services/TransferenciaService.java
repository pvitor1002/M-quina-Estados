package org.example.maquinaestados.application.services;

import org.example.maquinaestados.adapters.event.entity.Request;

public interface TransferenciaService {
    void execute(Request request);
}
