package com.dovile.bankspaymentstransfer.validator;

import com.dovile.bankspaymentstransfer.domain.request.PaymentsRequest;
import com.dovile.bankspaymentstransfer.exceptions.BadInputException;
import com.dovile.bankspaymentstransfer.exceptions.ResourceNotFoundException;
import com.dovile.bankspaymentstransfer.validator.checkiban_biccodes.CheckIbanValidation;

/**
 * @author Dovile Barkauskaite <barkauskaite.dovile@gmail.com>
 */
public class BasePaymentValidFields {
    /**
     * This methods call one class'es method {@link CheckIbanValidation} for credit and debit ibans validation.
     * Also checks request amount, which clients send to db.
     * Amount validation works: 1 check it is not null, 2 checks type, 3 amount shoud be possitve, 4 and number after dot, should be 2
     * @param paymentsRequest
     * @return true, if validation is correct.
     * @throws ResourceNotFoundException
     * @throws BadInputException
     */
    public boolean isValidThreeFields(PaymentsRequest paymentsRequest) throws ResourceNotFoundException, BadInputException {
        if (!(new CheckIbanValidation().isIBANValid(paymentsRequest.getDebtorIban()))) {
            throw new BadInputException("Debitor Iban is empty or incorrect");
        }
        if (!(new CheckIbanValidation().isIBANValid(paymentsRequest.getCreditorIban()))) {
            throw new BadInputException("Credit Iban is empty or incorrect");
        }
        return isValidAmount(paymentsRequest.getAmount());
    }

    //Valid Amount
    private boolean isValidAmount(String amount) throws BadInputException, ResourceNotFoundException {
        //Check it is empty
        if (amount == null) {
            throw new ResourceNotFoundException("Please fill in amount field");
        }
        //Check Type, should be digits
        Double newAmount;
        try {
            newAmount = Double.parseDouble(amount);
        } catch (Exception e) {
            throw new BadInputException("Bad amount type, should be just number");
        }
        //Amount not less than 0.01
        if (newAmount < 0.01) {
            throw new BadInputException("Minimum amount is 0.01");
        }
        //Check amount length after dot should be two numbers:
        if (amount.substring(amount.indexOf('.') + 1).length() > 2) {
            throw new BadInputException("Bad amount, after dot should be two numbers");
        }
        return true;
    }
}
