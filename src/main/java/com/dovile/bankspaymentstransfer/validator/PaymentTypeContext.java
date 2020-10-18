package com.dovile.bankspaymentstransfer.validator;

import com.dovile.bankspaymentstransfer.domain.request.PaymentsRequest;
import com.dovile.bankspaymentstransfer.exceptions.BadInputException;
import com.dovile.bankspaymentstransfer.exceptions.ResourceNotFoundException;

public class PaymentTypeContext {
    private PaymentTypeStrategy strategy;

    public PaymentTypeContext(PaymentTypeStrategy strategy){
        this.strategy = strategy;
    }
    public boolean ValidByTypeStrategy(PaymentsRequest paymentsRequest, String currency)
            throws ResourceNotFoundException, BadInputException {
        return strategy.getTypeValid(paymentsRequest, currency);
    }
}
