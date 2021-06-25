package org.example.maquinaestados.adapters.datastore;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.maquinaestados.adapters.datastore.entity.ControleEstadoEntity;
import org.example.maquinaestados.adapters.datastore.mapper.DatabaseMapper;
import org.example.maquinaestados.adapters.datastore.repository.StateControlRepository;
import org.example.maquinaestados.domain.entities.MaquinaEstado;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MaquinaEstadoDatastoreImpl implements MaquinaEstadoDatastore{

    private final StateControlRepository stateControlRepository;
    private final DatabaseMapper<ControleEstadoEntity, MaquinaEstado> mapper;

    @Override
    @SneakyThrows
    public Optional<MaquinaEstado> get(UUID id) {
        try{
            Optional<MaquinaEstado> maquinaEstado = this.stateControlRepository.findById(id)
                    .map(mapper::mapFromDatabase);
            return maquinaEstado;
        } catch(Exception e){
            throw new RuntimeException();
        }
    }

    @Override
    public boolean get(UUID id, String faseEvento) {
        try{
            boolean estado = this.stateControlRepository.findById(id)
                    .map(o -> o.getStateControlBody().equalsIgnoreCase(faseEvento)).isPresent();
            return estado;
        } catch(Exception e){
            throw e;
        }
    }

    @Override
    public void put(MaquinaEstado maquinaEstado) throws JsonProcessingException {
        ControleEstadoEntity controleEstadoEntity = this.mapper.mapToDatabase(maquinaEstado);
        try{
            stateControlRepository.save(controleEstadoEntity);
        } catch(Exception e){
            throw e;
        }
    }
}
