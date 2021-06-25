package org.example.maquinaestados.domain.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.ToString;
import org.example.maquinaestados.domain.types.TipoEventoTransferencia;
import org.example.statemachine.StateMachineContext;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
public class MaquinaEstado {
    private UUID id;
    private OffsetDateTime dataCriacao;
    private StateMachineContext<FaseEvento, TipoEventoTransferencia> context;
}
