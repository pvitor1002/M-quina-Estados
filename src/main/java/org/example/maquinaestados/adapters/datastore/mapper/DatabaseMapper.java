package org.example.maquinaestados.adapters.datastore.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface DatabaseMapper<TDatabase, TDomain> {
    TDomain mapFromDatabase(TDatabase in);
    TDatabase mapToDatabase(TDomain in) throws JsonProcessingException;
}
