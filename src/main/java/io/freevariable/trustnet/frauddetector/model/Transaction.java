package io.freevariable.trustnet.frauddetector.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    String transactionId;
    LocalDateTime timestamp;
    String accountId;
    Integer amount; // in cents
}
