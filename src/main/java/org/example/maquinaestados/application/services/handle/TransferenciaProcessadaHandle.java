package org.example.maquinaestados.application.services.handle;

import org.example.maquinaestados.domain.entities.FaseEvento;
import org.example.maquinaestados.domain.types.TipoEventoTransferencia;
import org.example.statemachine.EventContext;
import org.example.statemachine.TransitionAction;

public interface TransferenciaProcessadaHandle extends TransitionAction<TipoEventoTransferencia, EventContext<TipoEventoTransferencia>, FaseEvento> {
}
