package io.freevariable.trustnet.frauddetector;

import io.freevariable.trustnet.frauddetector.stream.KafkaStreamsRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FraudDetectorApplication {

    public static void main(String[] args) {
        SpringApplication.run(FraudDetectorApplication.class, args);
    }

    @Bean
    public CommandLineRunner clr(KafkaStreamsRunner runner) {
        return args -> {
            System.out.println("Running stream...");
            runner.start();
        };
    }
}
