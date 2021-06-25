package org.example.maquinaestados.adapters.datastore.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.example.maquinaestados.domain.entities.FaseEvento;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Column;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(value = "maquina_estado")
public class ControleEstadoEntity {

    @PrimaryKey(value = "codigo_correlacao")
    private UUID codigoCorrelacao;

    @Column(value = "state_control_body")
    private String stateControlBody;

    @Column(value = "data_hora_atualizacao")
    private Date dateTime;

    @Column(value = "codigo_estado")
    private FaseEvento eventType;
}
