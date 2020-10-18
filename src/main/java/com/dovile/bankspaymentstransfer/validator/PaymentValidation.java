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
    /**
     * to distribute by
     * used switch to helps call separate validation classes methods per types to check the fields
     *
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
            throw new ResourceNotFoundException("Type is Invalid");
        }
        switch (valueOf(type)) {
            case TYPE1:
                context = new PaymentTypeContext(new PaymentTypeOne());
                context.ValidByTypeStrategy(paymentsRequest, currency);
                break;
            case TYPE2:
                context = new PaymentTypeContext(new PaymentTypeTwo());
                context. ValidByTypeStrategy(paymentsRequest, currency);
                break;
            case TYPE3:
                context = new PaymentTypeContext(new PaymentTypeThree());
                context. ValidByTypeStrategy(paymentsRequest, currency);
                break;
            default:
                throw new ResourceNotFoundException("Not found type " + type);
        }
    }
}
