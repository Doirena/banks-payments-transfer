package com.dovile.bankspaymentstransfer.services;

import com.dovile.bankspaymentstransfer.domain.request.PaymentsRequest;
import com.dovile.bankspaymentstransfer.domain.response.CancelPaymentResponse;
import com.dovile.bankspaymentstransfer.domain.response.PaymentResponse;
import com.dovile.bankspaymentstransfer.domain.response.PaymentsIdResponse;
import com.dovile.bankspaymentstransfer.exceptions.BadInputException;
import com.dovile.bankspaymentstransfer.exceptions.ResourceNotFoundException;

import java.util.List;

/**
 *
 * @author Dovile Barkauskaite <barkauskaite.dovile@gmail.com>
 */
public interface PaymentService {
    /**
     * This method creates new Payment, which is saved in db.
     * Before creation all fields are validated by rules {@link com.dovile.bankspaymentstransfer.validator.PaymentValidation}.
     * After that all data comes to PaymentService which checks if types and currencies are valid by
     * {@link com.dovile.bankspaymentstransfer.repositories.PaymentTypeEntityRepository} and
     * {@link com.dovile.bankspaymentstransfer.repositories.CurrencyDataEntityRepository} and if it's valid returns a new payment.
     * @param paymentsRequest
     * @param type
     * @param currency
     * @return new Payment
     * @throws ResourceNotFoundException
     * @throws BadInputException
     */
    PaymentResponse createPayment(PaymentsRequest paymentsRequest, String type, String currency)
            throws ResourceNotFoundException, BadInputException;

    /** This method creates payment cancelation and calculates cancel fee based on the number of
     *  hours since the payment was created.
     * Also checks if payment can be canceled. If the day of the payment is different or payment
     * is already canceled, method returns exception.
     * @param paymentId
     * @return cancel Payment
     * @throws ResourceNotFoundException
     * @throws BadInputException
     */
    CancelPaymentResponse cancelPayment(Integer paymentId) throws ResourceNotFoundException, BadInputException;

    /**
     * This method returns the list of payments which status are true.
     * @return the list of payments which aren't canceled.
     */
    List<PaymentsIdResponse> getAllPaymentsId();

    /**
     * This method returns one canceled payment by paymentId. And returns payment Id and cancel fee.
     * @param paymentId
     * @return the canceled payment by payment id.
     */
    CancelPaymentResponse getCancelPaymentById(Integer paymentId) throws ResourceNotFoundException;
}
