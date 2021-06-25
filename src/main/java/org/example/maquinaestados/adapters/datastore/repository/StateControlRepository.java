package org.example.maquinaestados.adapters.datastore.repository;

import org.example.maquinaestados.adapters.datastore.entity.ControleEstadoEntity;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StateControlRepository extends ExtendedCassandraRepository<ControleEstadoEntity, UUID> {
}
