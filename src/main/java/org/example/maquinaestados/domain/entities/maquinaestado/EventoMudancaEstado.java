package org.example.maquinaestados.domain.entities.maquinaestado;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.example.maquinaestados.domain.types.TipoEventoTransferencia;
import org.example.statemachine.EventContext;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventoMudancaEstado<TEvent> implements EventContext<TipoEventoTransferencia> {
    private UUID id;
    private TipoEventoTransferencia tipoEventoTransferencia;
    private TEvent eventoOriginal;
    private TipoEventoTransferencia type;

    @Override
    public TipoEventoTransferencia getType() {
        return this.tipoEventoTransferencia;
    }
}
