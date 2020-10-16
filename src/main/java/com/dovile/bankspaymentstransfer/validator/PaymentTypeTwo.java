package com.dovile.bankspaymentstransfer.validator;

import com.dovile.bankspaymentstransfer.exceptions.BadInputException;

import static com.dovile.bankspaymentstransfer.validator.TypeEnum.TYPE2;

/**
 * @author Dovile Barkauskaite <barkauskaite.dovile@gmail.com>
 */
public class PaymentTypeTwo  {

    public boolean getTypeValid(String currency) throws BadInputException {
        if(!currency.equals(TYPE2.getTypeCurrency())){
            throw new BadInputException("Currency shoud be "+ TYPE2.getTypeCurrency());
        }
        return true;
    }
}
