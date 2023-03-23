package io.freevariable.trustnet.frauddetector.stream;

import io.freevariable.trustnet.frauddetector.model.Transaction;
import lombok.val;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;

import java.util.HashMap;

import static org.apache.kafka.streams.StreamsConfig.*;

@Configuration
public class StreamsConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    KafkaStreamsConfiguration kStreamsConfig() {
        val props = new HashMap<String, Object>();
        props.put(APPLICATION_ID_CONFIG, "fraud-detector");
        props.put(BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaStreamsConfiguration(props);
    }

    @Bean
    public StreamsBuilderFactoryBean kafkaStreamsBuilder(KafkaStreamsConfiguration streamsConfig) {
        return new StreamsBuilderFactoryBean(streamsConfig);
    }

    @Bean
    KafkaStreams kafkaStreams(StreamsBuilder streamsBuilder, KafkaStreamsConfiguration streamsConfig) {
        return new KafkaStreams(streamsBuilder.build(), streamsConfig.asProperties());
    }

}
