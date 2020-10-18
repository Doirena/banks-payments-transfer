package com.dovile.bankspaymentstransfer.validator;

import com.dovile.bankspaymentstransfer.domain.request.PaymentsRequest;
import com.dovile.bankspaymentstransfer.exceptions.BadInputException;
import com.dovile.bankspaymentstransfer.exceptions.ResourceNotFoundException;

import static com.dovile.bankspaymentstransfer.validator.TypeEnum.TYPE2;

/**
 *
 * @author Dovile Barkauskaite <barkauskaite.dovile@gmail.com>
 */
public class PaymentTypeTwo implements PaymentTypeStrategy {
    /**
     * Types have enum {@link TypeEnum}. Type 2 can have only USD currency, this method validates it.
     * This method calls {@link BasePaymentValidFields} class and validates other three fields.
     * @param currency
     * @return if valid returns true.
     * @throws BadInputException
     * @throws ResourceNotFoundException
     */
    public boolean getTypeValid(PaymentsRequest paymentsRequest, String currency)
            throws BadInputException, ResourceNotFoundException {

        if(new BasePaymentValidFields().isValidThreeFields(paymentsRequest)) {
                if (!currency.equals(TYPE2.getTypeCurrency())) {
                    throw new BadInputException("Currency should be " + TYPE2.getTypeCurrency());
                }
            }
        return true;
    }
}
