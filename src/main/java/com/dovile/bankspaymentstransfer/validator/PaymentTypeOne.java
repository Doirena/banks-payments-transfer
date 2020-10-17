package com.dovile.bankspaymentstransfer.validator;

import com.dovile.bankspaymentstransfer.domain.request.PaymentsRequest;
import com.dovile.bankspaymentstransfer.exceptions.BadInputException;

import static com.dovile.bankspaymentstransfer.validator.TypeEnum.TYPE1;

/**
 * @author Dovile Barkauskaite <barkauskaite.dovile@gmail.com>
 */
public class PaymentTypeOne{
    /**
     * Types have enum {@link TypeEnum}. Type 1 have just EUR currency, this method valid it.
     * Also by rules, Type1 should have details field, so this method checks it.
     * @param paymentsRequest
     * @param currency from request, which clients chosed
     * @return if valid is ok, return true.
     * @throws BadInputException
     */
    public boolean getTypeValid(PaymentsRequest paymentsRequest, String currency) throws BadInputException {
        if(!currency.equals(TYPE1.getTypeCurrency())){
            throw new BadInputException("Currency should be "+ TYPE1.getTypeCurrency());
        }
        if (paymentsRequest.getDetails() == null || paymentsRequest.getDetails().isEmpty()){
            throw new BadInputException("Please fill in the details field");
        }
        return true;
    }
}
