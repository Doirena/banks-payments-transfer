package com.dovile.bankspaymentstransfer.controller;

import com.dovile.bankspaymentstransfer.assemblers.CancelPaymentAssembler;
import com.dovile.bankspaymentstransfer.assemblers.PaymentModelAssembler;
import com.dovile.bankspaymentstransfer.domain.request.PaymentsRequest;
import com.dovile.bankspaymentstransfer.domain.response.CancelPaymentResponse;
import com.dovile.bankspaymentstransfer.domain.response.PaymentResponse;
import com.dovile.bankspaymentstransfer.domain.response.PaymentsIdResponse;
import com.dovile.bankspaymentstransfer.exceptions.BadInputException;
import com.dovile.bankspaymentstransfer.exceptions.ResourceNotFoundException;
import com.dovile.bankspaymentstransfer.assemblers.PaymentsIdModelAssembler;
import com.dovile.bankspaymentstransfer.services.PaymentService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author Dovile Barkauskaite <barkauskaite.dovile@gmail.com>
 */
@RestController
@Validated
public class PaymentController {

    private final PaymentService paymentService;
    private final PaymentsIdModelAssembler paymentsAssembler;
    private final CancelPaymentAssembler cancelPaymentAssembler;
    private final PaymentModelAssembler paymentModelAssembler;

    PaymentController(PaymentService paymentService,PaymentsIdModelAssembler paymentsAssembler,
                      CancelPaymentAssembler cancelPaymentAssembler, PaymentModelAssembler paymentModelAssembler) {
        this.paymentService = paymentService;
        this.paymentsAssembler = paymentsAssembler;
        this.cancelPaymentAssembler = cancelPaymentAssembler;
        this.paymentModelAssembler = paymentModelAssembler;
    }

    @PostMapping(value = "/createPayment", params = {"type", "currency"})
    public ResponseEntity<EntityModel<PaymentResponse>> createPayment(
            @Valid @RequestBody PaymentsRequest request,
            @RequestParam(value = "type") String type,
            @RequestParam(value = "currency") String currency) throws ResourceNotFoundException, BadInputException {
        EntityModel<PaymentResponse> entityModel = paymentModelAssembler.toModel(
                paymentService.createPayment(request, type, currency));
        return ResponseEntity.ok(entityModel);
    }

    @PostMapping("cancelPayment/{id}")
    public ResponseEntity<EntityModel<CancelPaymentResponse>> cancelPayment(
            @PathVariable(value = "id") Integer id) throws ResourceNotFoundException {
        EntityModel<CancelPaymentResponse> entityModel = cancelPaymentAssembler.toModel(paymentService.cancelPayment(id));
        return ResponseEntity.ok(entityModel);
    }

    @GetMapping("payments")
    public CollectionModel<EntityModel<PaymentsIdResponse>> getAllExistPayments() {
        List<EntityModel<PaymentsIdResponse>> employees = paymentService.getAllPaymentsId().stream()
                .map(paymentsAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(employees,
                linkTo(methodOn(PaymentController.class).getAllExistPayments()).withSelfRel());
    }

    @GetMapping("cancelPaymentFee/{id}")
    public EntityModel<CancelPaymentResponse> getCancelPaymentById(
            @PathVariable(value = "id") Integer id) throws ResourceNotFoundException {
        CancelPaymentResponse cancelPaymentResponse = paymentService.getCancelPaymentById(id);
        return cancelPaymentAssembler.toModel(cancelPaymentResponse);
    }
}
