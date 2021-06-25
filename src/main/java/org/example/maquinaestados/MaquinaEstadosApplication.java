package org.example.maquinaestados;

import org.example.maquinaestados.adapters.datastore.repository.ExtendedCassandraRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
@EnableCassandraRepositories(repositoryBaseClass = ExtendedCassandraRepositoryImpl.class)
public class MaquinaEstadosApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MaquinaEstadosApplication.class);
        app.run(args);
    }
}
