package io.freevariable.trustnet.frauddetector.stream;

import lombok.AllArgsConstructor;
import org.apache.kafka.streams.KafkaStreams;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class KafkaStreamsRunner {

    private final KafkaStreams kafkaStreams;

    public void start() {
        kafkaStreams.start();
        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));
    }

}
