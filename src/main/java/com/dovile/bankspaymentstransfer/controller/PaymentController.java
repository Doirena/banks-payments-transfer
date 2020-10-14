package com.dovile.bankspaymentstransfer.controller;

import com.dovile.bankspaymentstransfer.domain.request.PaymentsRequest;
import com.dovile.bankspaymentstransfer.domain.response.CancelPaymentResponse;
import com.dovile.bankspaymentstransfer.domain.response.PaymentsIdResponse;
import com.dovile.bankspaymentstransfer.entities.CancelPaymentEntity;
import com.dovile.bankspaymentstransfer.entities.PaymentsEntity;
import com.dovile.bankspaymentstransfer.exeptions.BadInputException;
import com.dovile.bankspaymentstransfer.exeptions.ResourceNotFoundException;
import com.dovile.bankspaymentstransfer.services.PaymentService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Dovile Barkauskaite <barkauskaite.dovile@gmail.com>
 */
@RestController
@Validated
public class PaymentController{

    private final PaymentService paymentService;


    PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping(value = "/createPayment", params = {"type", "currency"})
    public PaymentsEntity createPayment(
            @Valid @RequestBody PaymentsRequest request,
            @RequestParam(value = "type") String type,
            @RequestParam(value = "currency") String currency) throws ResourceNotFoundException, BadInputException {
        return paymentService.createPayment(request,type,currency);
    }

    @PostMapping("cancelPayment/{id}")
    public CancelPaymentEntity cancelPayment(
            @PathVariable(value = "id") Integer id)throws ResourceNotFoundException {
        return paymentService.cancelPayment(id);
    }

        @GetMapping("payments")
    public List<PaymentsIdResponse> getAllExistPayments() {
        return paymentService.getAllPaymentsId();
    }

    @GetMapping("cancelPaymentFee/{id}")
    public CancelPaymentResponse getAllCancelPayments(
            @PathVariable(value = "id") Integer id) throws ResourceNotFoundException {
        return paymentService.getCancelPaymentById(id);
    }
}
