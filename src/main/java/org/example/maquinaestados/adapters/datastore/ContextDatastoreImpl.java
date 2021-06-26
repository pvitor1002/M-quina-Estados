package org.example.maquinaestados.adapters.datastore;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class ContextDatastoreImpl implements ContextDatastore{

    private static ThreadLocal<Map<String, Object>> kafkaHeaders = new ThreadLocal<>();

    @Override
    public void setHeaders(Map<String, Object> headers){
        kafkaHeaders.set(headers);
    }

    @Override
    public Map<String, Object> getHeaders(){
        if(Objects.isNull(kafkaHeaders.get()))
            return new HashMap<>();
        return kafkaHeaders.get();
    }
}
