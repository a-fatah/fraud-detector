package io.freevariable.trustnet.frauddetector.stream;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;

@Configuration
public class StreamsConfig {

    @Bean
    public StreamsBuilderFactoryBean kafkaStreamsBuilder(KafkaStreamsConfiguration streamsConfig) {
        return new StreamsBuilderFactoryBean(streamsConfig);
    }

    @Bean
    KafkaStreams kafkaStreams(StreamsBuilder streamsBuilder, KafkaStreamsConfiguration streamsConfig) {
        return new KafkaStreams(streamsBuilder.build(), streamsConfig.asProperties());
    }

    @Bean
    KafkaStreamsRunner kafkaStreamsRunner(KafkaStreams streams) {
        return new KafkaStreamsRunner(streams);
    }
}
