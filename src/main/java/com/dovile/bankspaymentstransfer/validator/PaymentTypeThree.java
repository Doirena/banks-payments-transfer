package com.dovile.bankspaymentstransfer.validator;

import com.dovile.bankspaymentstransfer.domain.request.PaymentsRequest;
import com.dovile.bankspaymentstransfer.exceptions.BadInputException;
import com.dovile.bankspaymentstransfer.exceptions.ResourceNotFoundException;
import com.dovile.bankspaymentstransfer.validator.checkiban_biccodes.CheckBicCode;

import static com.dovile.bankspaymentstransfer.validator.TypeEnum.TYPE3;

/**
 *
 * @author Dovile Barkauskaite <barkauskaite.dovile@gmail.com>
 */
public class PaymentTypeThree implements PaymentTypeStrategy {
    /**
     * Types have enum {@link TypeEnum}. Type 3 can have only EUR and USD currencies.
     * Also Type 3 is mandatory for bic code. This method calls {@link CheckBicCode} class method isBICCodeValid
     * and checks if it's valid. Returns true or false.
     * This method calls {@link BasePaymentValidFields} class and validates other three fields.
     * @param paymentsRequest
     * @param currency
     * @return if valid returns true.
     * @throws BadInputException
     * @throws ResourceNotFoundException
     */
    public boolean getTypeValid(PaymentsRequest paymentsRequest, String currency)
            throws BadInputException, ResourceNotFoundException {

        if (new BasePaymentValidFields().isValidThreeFields(paymentsRequest)) {
            if (!(currency.equals(TYPE3.getTypeCurrency()) || currency.equals(TYPE3.getTypeThreeCurrency()))) {
                throw new BadInputException(
                        "Currency shoud be " + TYPE3.getTypeCurrency() + " or " + TYPE3.getTypeThreeCurrency());
            }
            if (!new CheckBicCode().isBICCodeValid(paymentsRequest.getBic_code())) {
                throw new BadInputException("Please enter right Bic Code");
            }
        }
        return true;
    }
}
