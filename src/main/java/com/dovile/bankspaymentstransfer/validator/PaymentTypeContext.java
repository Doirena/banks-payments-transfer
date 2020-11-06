package com.dovile.bankspaymentstransfer.validator;

import com.dovile.bankspaymentstransfer.domain.request.PaymentsRequest;
import com.dovile.bankspaymentstransfer.exceptions.BadInputException;
import com.dovile.bankspaymentstransfer.exceptions.ResourceNotFoundException;

/**
 *
 * @author Dovile Barkauskaite <barkauskaite.dovile@gmail.com>
 */
public class PaymentTypeContext<T extends PaymentTypeStrategy> {

//    private PaymentTypeStrategy strategy;
        private T strategy;

    public PaymentTypeContext(T obj){
        this.strategy = obj;
    }
    public boolean validByTypeStrategy(PaymentsRequest paymentsRequest, String currency)
            throws ResourceNotFoundException, BadInputException {
        return strategy.getTypeValid(paymentsRequest, currency);
    }
}
