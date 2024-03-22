package org.nedioit.kafkademoindatacore;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class KafkaDemoIndatacoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaDemoIndatacoreApplication.class, args);
    }


}
