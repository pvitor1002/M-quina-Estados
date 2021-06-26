package org.example.maquinaestados.adapters.datastore;

import java.util.Map;

public interface ContextDatastore {

    void setHeaders(Map<String, Object> headers);

    Map<String, Object> getHeaders();
}
