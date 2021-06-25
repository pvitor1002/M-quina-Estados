package org.example.maquinaestados.application.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.example.maquinaestados.adapters.datastore.MaquinaEstadoDatastore;
import org.example.maquinaestados.domain.entities.FaseEvento;
import org.example.maquinaestados.domain.entities.MaquinaEstado;
import org.example.maquinaestados.domain.types.TipoEventoTransferencia;
import org.example.statemachine.StateMachine;
import org.example.statemachine.StateMachineBuilder;
import org.example.statemachine.StateMachineContext;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.time.OffsetDateTime;

@RequiredArgsConstructor
@Component
public class StateMachineServiceImpl implements StateMachineService{

    private final MaquinaEstadoDatastore estadoDatastore;

    @Override
    public StateMachine<FaseEvento, TipoEventoTransferencia> createStateMachine(UUID id) throws JsonProcessingException {
        MaquinaEstado maquinaEstado = MaquinaEstado.builder()
                .id(id)
                .dataCriacao(OffsetDateTime.now())
                .context(new StateMachineContext<>())
                .build();

        StateMachine<FaseEvento, TipoEventoTransferencia> stateMachine = getStateMachineBuilder()
                .build(maquinaEstado.getContext());

        this.estadoDatastore.put(maquinaEstado);

        return stateMachine;
    }

    @Override
    public StateMachine<FaseEvento, TipoEventoTransferencia> getStateMachine(UUID id) throws JsonProcessingException {
        MaquinaEstado maquinaEstado = estadoDatastore.get(id).orElseThrow(() -> {
            return new RuntimeException();
        });
        return getStateMachineBuilder().build(maquinaEstado.getContext());
    }

    @Override
    public void salvarStateMachine(UUID id, StateMachine<FaseEvento, TipoEventoTransferencia> stateMachine) throws JsonProcessingException {
        MaquinaEstado maquinaEstado = estadoDatastore.get(id).orElseThrow(() -> {
            return new RuntimeException();
        });

        maquinaEstado.setContext(stateMachine.getContext());

        estadoDatastore.put(maquinaEstado);
    }

    private StateMachineBuilder<FaseEvento,TipoEventoTransferencia> getStateMachineBuilder() {
        return StateMachineBuilder.create(FaseEvento.class, TipoEventoTransferencia.class)

                .initial(FaseEvento.TRANSFERENCIA_SOLICITADA)

                .addTransition(FaseEvento.TRANSFERENCIA_SOLICITADA, FaseEvento.TRANSFERENCIA_REALIZADA)
                .withEvent(TipoEventoTransferencia.SENHA_VALIDADA);


    }
}
