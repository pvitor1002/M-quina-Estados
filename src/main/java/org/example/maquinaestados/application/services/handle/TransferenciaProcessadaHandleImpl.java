package org.example.maquinaestados.application.services.handle;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.example.maquinaestados.adapters.event.entity.Response;
import org.example.maquinaestados.adapters.event.producer.TransferenciaProducer;
import org.example.maquinaestados.domain.entities.FaseEvento;
import org.example.maquinaestados.domain.types.TipoEventoTransferencia;
import org.example.statemachine.EventContext;
import org.example.statemachine.StateMachineContext;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TransferenciaProcessadaHandleImpl implements TransferenciaProcessadaHandle{

    private final TransferenciaProducer transferenciaProducer;

    @Override
    public void execute(EventContext<TipoEventoTransferencia> context, TipoEventoTransferencia tipoEventoTransferencia,
                        StateMachineContext<FaseEvento, TipoEventoTransferencia> stateMachineContext) {

        try {
            System.out.println("Estado da transferencia alterada");
            Response response = new Response();
            response.setOrigin(1);
            response.setMessage("Chegamos ao final do teste da máquina");
            response.setId(UUID.randomUUID().toString());
            transferenciaProducer.produce(response);
        } catch (JsonProcessingException e){
            System.err.println("erro de Object Mapper");
        }
    }
}
