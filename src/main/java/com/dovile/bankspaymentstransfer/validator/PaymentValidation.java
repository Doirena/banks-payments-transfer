package com.dovile.bankspaymentstransfer.validator;

import com.dovile.bankspaymentstransfer.domain.request.PaymentsRequest;
import com.dovile.bankspaymentstransfer.exceptions.BadInputException;
import com.dovile.bankspaymentstransfer.exceptions.ResourceNotFoundException;

import java.util.Locale;

import static com.dovile.bankspaymentstransfer.validator.TypeEnum.valueOf;

/**
 *
 * @author Dovile Barkauskaite <barkauskaite.dovile@gmail.com>
 */
public class PaymentValidation {
    /**
     * This method let to distribute validation by types with appropriate validation rules.
     * @param paymentsRequest
     * @param type            which clients chose
     * @param currency        which clients chose
     * @throws BadInputException
     * @throws ResourceNotFoundException
     */
    public void isValidInput(PaymentsRequest paymentsRequest, String type, String currency) throws BadInputException, ResourceNotFoundException {
        type = type.replaceAll("\\s", "").toUpperCase(Locale.ROOT);
        currency = currency.replaceAll("\\s", "").toUpperCase(Locale.ROOT);
        PaymentTypeContext context;
        try {
            valueOf(type);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Type is invalid");
        }
        switch (valueOf(type)) {
            case TYPE1:
                context = new PaymentTypeContext(new PaymentTypeOne());
                context.validByTypeStrategy(paymentsRequest, currency);
                break;
            case TYPE2:
                context = new PaymentTypeContext(new PaymentTypeTwo());
                context.validByTypeStrategy(paymentsRequest, currency);
                break;
            case TYPE3:
                context = new PaymentTypeContext(new PaymentTypeThree());
                context.validByTypeStrategy(paymentsRequest, currency);
                break;
            default:
                throw new ResourceNotFoundException("Not found type " + type);
        }
    }
}
