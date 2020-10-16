package com.dovile.bankspaymentstransfer.validator;

import com.dovile.bankspaymentstransfer.domain.request.PaymentsRequest;
import com.dovile.bankspaymentstransfer.exceptions.BadInputException;

import static com.dovile.bankspaymentstransfer.validator.TypeEnum.TYPE1;

/**
 * @author Dovile Barkauskaite <barkauskaite.dovile@gmail.com>
 */
public class PaymentTypeOne{

    public boolean getTypeValid(PaymentsRequest paymentsRequest, String currency) throws BadInputException {
        if(!currency.equals(TYPE1.getTypeCurrency())){
            throw new BadInputException("Currency shoud be "+ TYPE1.getTypeCurrency());
        }
        if (paymentsRequest.getDetails() == null){
            throw new BadInputException("Please fill in the details field.");
        }
        return true;
    }
}
