package io.freevariable.trustnet.frauddetector.stream;

import io.freevariable.trustnet.frauddetector.model.Transaction;
import io.freevariable.trustnet.frauddetector.service.FraudDetectorService;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class FraudDetectionTopology {

    private final String inputTopic = "transactions";
    private final String outputTopic = "fraudulent-transactions";

    private final FraudDetectorService fraudDetectorService;
    private final StreamsBuilder streamsBuilder;
    private final KafkaTemplate<String, Long> kafkaTemplate;

    public FraudDetectionTopology(FraudDetectorService fraudDetectorService,
                                  StreamsBuilder streamsBuilder,
                                  KafkaTemplate<String, Long> kafkaTemplate) {
        this.fraudDetectorService = fraudDetectorService;
        this.streamsBuilder = streamsBuilder;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Bean
    public KStream<String, Transaction> kStream() {
        KStream<String, Transaction> stream = streamsBuilder.stream(inputTopic);

        stream.filter((key, transaction) -> fraudDetectorService.isFraudulent(transaction))
                .map((key, transaction) -> KeyValue.pair(transaction.getTransactionId(), transaction))
                .groupByKey()
                .windowedBy(TimeWindows.ofSizeAndGrace(Duration.ofMinutes(1), Duration.ofMinutes(1)))
                .count()
                .filter((accountId, count) -> count >= 5)
                .toStream()
                .map((windowedAccountId, count) -> KeyValue.pair(windowedAccountId.key(), count))
                .to(outputTopic);

        return stream;
    }
}
