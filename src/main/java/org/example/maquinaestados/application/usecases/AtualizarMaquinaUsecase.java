package org.example.maquinaestados.application.usecases;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.maquinaestados.domain.entities.maquinaestado.EventoMudancaEstado;
import org.springframework.messaging.MessageHeaders;

public interface AtualizarMaquinaUsecase {
    void execute(EventoMudancaEstado eventoMudancaEstado) throws JsonProcessingException;
}
