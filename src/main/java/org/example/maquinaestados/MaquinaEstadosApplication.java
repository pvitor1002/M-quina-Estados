package org.example.maquinaestados;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class MaquinaEstadosApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MaquinaEstadosApplication.class);
        app.run(args);
    }
}
