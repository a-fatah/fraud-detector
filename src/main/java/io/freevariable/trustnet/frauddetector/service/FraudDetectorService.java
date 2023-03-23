package io.freevariable.trustnet.frauddetector.service;

import io.freevariable.trustnet.frauddetector.model.Transaction;

public interface FraudDetectorService {
    boolean isFraudulent(Transaction transaction);
}
