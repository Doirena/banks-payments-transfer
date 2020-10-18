package com.dovile.bankspaymentstransfer.validator;

import com.dovile.bankspaymentstransfer.domain.request.PaymentsRequest;
import com.dovile.bankspaymentstransfer.exceptions.BadInputException;
import com.dovile.bankspaymentstransfer.exceptions.ResourceNotFoundException;

import static com.dovile.bankspaymentstransfer.validator.TypeEnum.TYPE1;

/**
 *
 * @author Dovile Barkauskaite <barkauskaite.dovile@gmail.com>
 */
public class PaymentTypeOne implements PaymentTypeStrategy {

    /**
     * Types have enum {@link TypeEnum}. Type 1 can only have EUR currency, this method valids it.
     * Also by rules, Type1 should have details field and this method checks it.
     * This method calls {@link BasePaymentValidFields} class and validates other three fields.
     * @param paymentsRequest
     * @param currency
     * @return if valid is ok, returns true
     * @throws BadInputException
     * @throws ResourceNotFoundException
     */
    public boolean getTypeValid(PaymentsRequest paymentsRequest, String currency)
            throws BadInputException, ResourceNotFoundException {

        if(new BasePaymentValidFields().isValidThreeFields(paymentsRequest)) {
            if (!currency.equals(TYPE1.getTypeCurrency())) {
                throw new BadInputException("Currency should be " + TYPE1.getTypeCurrency());
            }
            if (paymentsRequest.getDetails() == null || paymentsRequest.getDetails().isEmpty()) {
                throw new BadInputException("Please fill in the details field");
            }
        }
        return true;
    }
}
