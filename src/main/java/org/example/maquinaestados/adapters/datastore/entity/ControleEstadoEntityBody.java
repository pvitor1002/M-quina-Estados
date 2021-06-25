package org.example.maquinaestados.adapters.datastore.entity;

import org.example.maquinaestados.domain.entities.FaseEvento;
import org.example.maquinaestados.domain.types.TipoEventoTransferencia;
import org.example.statemachine.StateMachineContext;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ControleEstadoEntityBody {
    private StateMachineContext<FaseEvento, TipoEventoTransferencia> stateMachineContext;
}
