package com.dovile.bankspaymentstransfer.validator;

import com.dovile.bankspaymentstransfer.domain.request.PaymentsRequest;
import com.dovile.bankspaymentstransfer.exceptions.BadInputException;
import com.dovile.bankspaymentstransfer.exceptions.ResourceNotFoundException;

/**
 *
 * @author Dovile Barkauskaite <barkauskaite.dovile@gmail.com>
 */
public interface PaymentTypeStrategy {
    /**
     * This method make validation for all enter field.
     * Separate class implement this interface and has this method with different rules of validation.
     * @param paymentsRequest data which client enter
     * @param currency which client choose
     * @return true if valid is ok
     * @throws BadInputException
     * @throws ResourceNotFoundException
     */
    public boolean getTypeValid(PaymentsRequest paymentsRequest, String currency)
            throws BadInputException, ResourceNotFoundException;
}
