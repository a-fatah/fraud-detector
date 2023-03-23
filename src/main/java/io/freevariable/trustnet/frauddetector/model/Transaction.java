package io.freevariable.trustnet.frauddetector.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Transaction {
    String transactionId;
    LocalDateTime timestamp;
    String accountId;
    Integer amount; // in cents
}
