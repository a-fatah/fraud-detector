package io.freevariable.trustnet.frauddetector;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.freevariable.trustnet.frauddetector.model.Transaction;
import io.freevariable.trustnet.frauddetector.service.FraudDetectorService;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.function.Function;

@SpringBootApplication
public class FraudDetectorApplication {

    public static void main(String[] args) {
        SpringApplication.run(FraudDetectorApplication.class, args);
    }

    @Bean
    Function<KStream<String, Transaction>, KStream<String, Long>> fraudDetector(FraudDetectorService fraudDetectorService) {
        return stream ->
                stream.filter((key, transaction) ->
                        fraudDetectorService.isFraudulent(transaction))
                        .map((key, transaction) -> KeyValue.pair(transaction.getAccountId(), transaction))
                        .groupByKey()
                        .windowedBy(TimeWindows.ofSizeAndGrace(Duration.ofSeconds(5), Duration.ofSeconds(1)))
                        .count()
                        .filter((accountId, count) -> count >= 5)
                        .toStream()
                        .map((windowedAccountId, count) -> KeyValue.pair(windowedAccountId.key(), count));
    }

     @Bean
     public ObjectMapper objectMapper() {
         ObjectMapper objectMapper = new ObjectMapper();
         objectMapper.registerModule(new JavaTimeModule());
         return objectMapper;
     }

}
