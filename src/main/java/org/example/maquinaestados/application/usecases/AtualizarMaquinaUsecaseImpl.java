package org.example.maquinaestados.application.usecases;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.example.maquinaestados.adapters.datastore.ContextDatastore;
import org.example.maquinaestados.application.services.StateMachineService;
import org.example.maquinaestados.application.services.handle.MaquinaCriadaHandle;
import org.example.maquinaestados.domain.entities.FaseEvento;
import org.example.maquinaestados.domain.entities.maquinaestado.EventoMudancaEstado;
import org.example.maquinaestados.domain.types.TipoEventoTransferencia;
import org.example.statemachine.StateMachine;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AtualizarMaquinaUsecaseImpl implements AtualizarMaquinaUsecase{

    private final StateMachineService stateMachineService;
    private final MaquinaCriadaHandle maquinaCriadaHandle;

    @Override
    public void execute(EventoMudancaEstado eventoMudancaEstado) throws JsonProcessingException {

        if(TipoEventoTransferencia.CRIAR_MAQUINA_ESTADOS.equals(eventoMudancaEstado.getType())){
            stateMachineService.createStateMachine(eventoMudancaEstado.getId());

            maquinaCriadaHandle.transferir(eventoMudancaEstado.getId());
        } else {
            StateMachine<FaseEvento, TipoEventoTransferencia> stateMachine = stateMachineService
                    .getStateMachine(eventoMudancaEstado.getId());
            if(stateMachine.sendEvent(eventoMudancaEstado)){
                stateMachineService.salvarStateMachine(eventoMudancaEstado.getId(), stateMachine);
            }
        }
    }
}
