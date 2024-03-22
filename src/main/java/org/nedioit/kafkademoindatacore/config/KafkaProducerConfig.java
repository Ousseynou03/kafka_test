package org.nedioit.kafkademoindatacore.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.security.plain.PlainLoginModule;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;


import java.io.File;
import java.io.IOException;
import java.util.*;

@Configuration
@PropertySource("${spring.config.name}")
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${ACKS_CONFIG}")
    private String acks_config;

    @Value("${topic}")
    private String topic;

    @Value("${username}")
    private String username;

    @Value("${password}")
    private String password;

    // In Map<String, Object>, we were going to replace Object for a class name if we had declared an entity
    public Map<String, Object> producerConfig(){
        Map<String,Object> props = new HashMap<>();
        // Spécification of server bootstrap
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
        props.put(ProducerConfig.ACKS_CONFIG,acks_config);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
// Configuration SASL SCRAM-SHA-512
        props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_PLAINTEXT");
        props.put(SaslConfigs.SASL_MECHANISM, "SCRAM-SHA-512");
        props.put(SaslConfigs.SASL_JAAS_CONFIG,
                "org.apache.kafka.common.security.scram.ScramLoginModule required username='" + username + "' password='" + password + "';");
        return props;
    }


    // ProducerFactory => fabriquant de producer
    @Bean
    public ProducerFactory<String, String> producerFactory(){
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate(
            ProducerFactory<String, String> producerFactory
    ){
        return new KafkaTemplate<>(producerFactory);
    }


    @Bean
    public CommandLineRunner kafkaMessageSender(KafkaTemplate<String, String> kafkaTemplate) {
        return args -> {
            // Messages à envoyer
            List<String> messages = Arrays.asList(
                    "first message",
                    "second message",
                    "third message"

            );

            // Parcourir la liste des messages
            for (String message : messages) {
                // Générer une clé unique pour chaque message
                String key = UUID.randomUUID().toString();
                // Envoyer le message avec la clé générée
                kafkaTemplate.send(topic, key, message);
            }
        };
    }





}

