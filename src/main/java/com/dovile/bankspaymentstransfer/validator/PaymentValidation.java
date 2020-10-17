package com.dovile.bankspaymentstransfer.validator;

import com.dovile.bankspaymentstransfer.domain.request.PaymentsRequest;
import com.dovile.bankspaymentstransfer.exceptions.BadInputException;
import com.dovile.bankspaymentstransfer.exceptions.ResourceNotFoundException;

import java.util.Locale;
import static com.dovile.bankspaymentstransfer.validator.TypeEnum.valueOf;

/**
 * @author Dovile Barkauskaite <barkauskaite.dovile@gmail.com>
 */
public class PaymentValidation {

    public void isValidInput(PaymentsRequest paymentsRequest,String type, String currency) throws BadInputException, ResourceNotFoundException {
   boolean mean = false;
        type = type.replaceAll("\\s", "").toUpperCase(Locale.ROOT);
        currency = currency.replaceAll("\\s", "").toUpperCase(Locale.ROOT);
        if (new BasePaymentValidFields().isValidThreeFields(paymentsRequest)) {
            try {
                valueOf(type);
            } catch (Exception e) {
                throw new ResourceNotFoundException("Type is Invalid ");
            }
            switch (valueOf(type)) {
                case TYPE1:
                    new PaymentTypeOne().getTypeValid(paymentsRequest, currency);
                    break;
                case TYPE2:
                    new PaymentTypeTwo().getTypeValid(currency);
                    break;
                case TYPE3:
                    new PaymentTypeThree().getTypeValid(paymentsRequest, currency);
                    break;
                default:
                    throw new ResourceNotFoundException("Not found type " + type);
            }
        }
    }
}