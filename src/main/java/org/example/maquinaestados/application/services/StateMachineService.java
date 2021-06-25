package org.example.maquinaestados.application.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.maquinaestados.domain.entities.FaseEvento;
import org.example.maquinaestados.domain.types.TipoEventoTransferencia;
import org.example.statemachine.StateMachine;

import java.util.UUID;

public interface StateMachineService {

    StateMachine<FaseEvento, TipoEventoTransferencia> createStateMachine(UUID id) throws JsonProcessingException;

    StateMachine<FaseEvento, TipoEventoTransferencia> getStateMachine(UUID id) throws JsonProcessingException;

    void salvarStateMachine(UUID id, StateMachine<FaseEvento, TipoEventoTransferencia> stateMachine) throws JsonProcessingException;
}
