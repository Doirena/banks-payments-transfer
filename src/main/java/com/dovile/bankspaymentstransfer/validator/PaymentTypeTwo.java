package com.dovile.bankspaymentstransfer.validator;

import com.dovile.bankspaymentstransfer.exceptions.BadInputException;

import static com.dovile.bankspaymentstransfer.validator.TypeEnum.TYPE2;

/**
 * @author Dovile Barkauskaite <barkauskaite.dovile@gmail.com>
 */
public class PaymentTypeTwo  {
    /**
     * Types have enum {@link TypeEnum}. Type 2 have just USD currency, this method valid it.
     * @param currency from request, which clients chosed
     * @return if valid is ok, return true.
     * @throws BadInputException
     */

    public boolean getTypeValid(String currency) throws BadInputException {
        if(!currency.equals(TYPE2.getTypeCurrency())){
            throw new BadInputException("Currency should be "+ TYPE2.getTypeCurrency());
        }
        return true;
    }
}
