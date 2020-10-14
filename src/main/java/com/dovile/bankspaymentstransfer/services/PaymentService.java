package com.dovile.bankspaymentstransfer.services;

import com.dovile.bankspaymentstransfer.domain.request.PaymentsRequest;
import com.dovile.bankspaymentstransfer.domain.response.CancelPaymentResponse;
import com.dovile.bankspaymentstransfer.domain.response.PaymentsIdResponse;
import com.dovile.bankspaymentstransfer.entities.CancelPaymentEntity;
import com.dovile.bankspaymentstransfer.entities.PaymentsEntity;
import com.dovile.bankspaymentstransfer.exceptions.BadInputException;
import com.dovile.bankspaymentstransfer.exceptions.ResourceNotFoundException;

import java.util.List;

/**
 * @author Dovile Barkauskaite <barkauskaite.dovile@gmail.com>
 */
public interface PaymentService {
    /**
     *
     * @param request
     * @param type
     * @param currency
     * @return new Payments
     */
    PaymentsEntity createPayment(PaymentsRequest request, String type, String currency) throws ResourceNotFoundException, BadInputException;

    /**
     *
     * @param id
     * @return cancel Payment
     * @throws ResourceNotFoundException
     */
    CancelPaymentEntity cancelPayment(Integer id) throws ResourceNotFoundException;

    /**
     *
     * @return list of payments which aren't cancel
     */
    List<PaymentsIdResponse> getAllPaymentsId();

    /**
     *
     * @param id
     * @return cancel payment(payment id and cancel fee) by payment id
     */
    CancelPaymentResponse getCancelPaymentById(Integer id) throws ResourceNotFoundException;
}
