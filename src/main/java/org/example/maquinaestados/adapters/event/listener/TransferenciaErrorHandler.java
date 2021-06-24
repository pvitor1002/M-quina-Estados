package org.example.maquinaestados.adapters.event.listener;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.ErrorHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransferenciaErrorHandler implements ErrorHandler {
    @Override
    public void handle(Exception e, ConsumerRecord<?, ?> data) {

    }

    @Override
    public void handle(Exception e, ConsumerRecord<?, ?> data, Consumer<?, ?> consumer) {
        consumer.commitAsync();
    }
}
