package io.freevariable.trustnet.frauddetector.service;

import io.freevariable.trustnet.frauddetector.model.Transaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FraudDetectorServiceImpl implements FraudDetectorService {

    @Value("${fraud-detector.fraud.threshold}")
    private Integer fraudThreshold;

    @Override
    public boolean isFraudulent(Transaction transaction) {
        if (transaction.getAmount().compareTo(fraudThreshold) > 0) {
            return true;
        }
        return false;
    }

}
