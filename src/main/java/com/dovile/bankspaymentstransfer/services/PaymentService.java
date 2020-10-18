package com.dovile.bankspaymentstransfer.services;

import com.dovile.bankspaymentstransfer.domain.request.PaymentsRequest;
import com.dovile.bankspaymentstransfer.domain.response.CancelPaymentResponse;
import com.dovile.bankspaymentstransfer.domain.response.PaymentResponse;
import com.dovile.bankspaymentstransfer.domain.response.PaymentsIdResponse;
import com.dovile.bankspaymentstransfer.exceptions.BadInputException;
import com.dovile.bankspaymentstransfer.exceptions.ResourceNotFoundException;

import java.util.List;

/**
 * @author Dovile Barkauskaite <barkauskaite.dovile@gmail.com>
 */
public interface PaymentService {
    /**
     * This method create new Payment, which save in db. But first before entering paymentsRequest,
     * which choose client, all field is validation by rules {@link com.dovile.bankspaymentstransfer.validator.PaymentValidation}.
     * After that all data comes to service, checks if types and currency are valid by
     * {@link com.dovile.bankspaymentstransfer.repositories.PaymentTypeEntityRepository} and
     * {@link com.dovile.bankspaymentstransfer.repositories.CurrencyDataEntityRepository} and if it's true return new payment.
     * @param paymentsRequest
     * @param type
     * @param currency
     * @return new Payments
     */
    PaymentResponse createPayment(PaymentsRequest paymentsRequest, String type, String currency, String ipAddres)
            throws ResourceNotFoundException, BadInputException;

    /** This method create cancel payment and calculate cancel fee based on the number of hours since the payment was created.
     * Also check if this payments can be canceled. If day is different or payments is already canceled, method return exception.
     * @param paymentId
     * @return cancel Payment
     * @throws ResourceNotFoundException
     */
    CancelPaymentResponse cancelPayment(Integer paymentId) throws ResourceNotFoundException, BadInputException;

    /**
     * This methods returns list of payments which status is true, and they are not canceled.
     * @return list of payments which aren't cancel
     */
    List<PaymentsIdResponse> getAllPaymentsId();

    /**
     * This method return one cancel payment, by paymentd. And returns payment Id and cancel fee.
     * @param paymentId
     * @return cancel payment by payment id
     */
    CancelPaymentResponse getCancelPaymentById(Integer paymentId) throws ResourceNotFoundException;
}
