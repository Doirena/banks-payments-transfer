package com.dovile.bankspaymentstransfer.validator;

import com.dovile.bankspaymentstransfer.domain.request.PaymentsRequest;
import com.dovile.bankspaymentstransfer.exceptions.BadInputException;
import com.dovile.bankspaymentstransfer.validator.checkiban_biccodes.CheckBicCode;

import static com.dovile.bankspaymentstransfer.validator.TypeEnum.TYPE3;

/**
 * @author Dovile Barkauskaite <barkauskaite.dovile@gmail.com>
 */
public class PaymentTypeThree  {

    public boolean getTypeValid(PaymentsRequest paymentsRequest, String currency) throws BadInputException {
        if(!(currency.equals(TYPE3.getTypeCurrency()) || currency.equals(TYPE3.getTypeThreeCurrency())) ){
            throw new BadInputException(
                    "Currency shoud be "+ TYPE3.getTypeCurrency()+" or "+ TYPE3.getTypeThreeCurrency());
        }

        if(!new CheckBicCode().isBICCodeValid(paymentsRequest.getBic_code())){
            throw new BadInputException("Please enter right Bic Code");
        }
        return true;
    }
}
