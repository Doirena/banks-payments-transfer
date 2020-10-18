package com.dovile.bankspaymentstransfer.controller;

import com.dovile.bankspaymentstransfer.assemblers.CancelPaymentAssembler;
import com.dovile.bankspaymentstransfer.domain.request.PaymentsRequest;
import com.dovile.bankspaymentstransfer.domain.response.CancelPaymentResponse;
import com.dovile.bankspaymentstransfer.domain.response.PaymentResponse;
import com.dovile.bankspaymentstransfer.domain.response.PaymentsIdResponse;
import com.dovile.bankspaymentstransfer.exceptions.BadInputException;
import com.dovile.bankspaymentstransfer.exceptions.ResourceNotFoundException;
import com.dovile.bankspaymentstransfer.assemblers.PaymentsIdModelAssembler;
import com.dovile.bankspaymentstransfer.services.ClientCountryService;
import com.dovile.bankspaymentstransfer.services.PaymentService;
import com.dovile.bankspaymentstransfer.validator.PaymentValidation;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author Dovile Barkauskaite <barkauskaite.dovile@gmail.com>
 */
@RestController
@RequestMapping("/payments")
@Validated
public class PaymentController {

    private PaymentValidation paymentValidation = new PaymentValidation();

    private final PaymentService paymentService;
    private final PaymentsIdModelAssembler paymentsAssembler;
    private final CancelPaymentAssembler cancelPaymentAssembler;
    private final ClientCountryService clientCountryService;

    PaymentController(PaymentService paymentService, ClientCountryService clientCountryService,
                      PaymentsIdModelAssembler paymentsAssembler, CancelPaymentAssembler cancelPaymentAssembler) {
        this.paymentService = paymentService;
        this.clientCountryService=clientCountryService;
        this.paymentsAssembler = paymentsAssembler;
        this.cancelPaymentAssembler = cancelPaymentAssembler;
    }

    @PostMapping(value = "/create", params = {"type", "currency"})
        public ResponseEntity<PaymentResponse> createPayment(
            @Valid @RequestBody PaymentsRequest paymentsRequest,
            @RequestParam(value = "type") String type,
            @RequestParam(value = "currency") String currency, HttpServletRequest request)
            throws ResourceNotFoundException, BadInputException {

        paymentValidation.isValidInput(paymentsRequest, type, currency);
        clientCountryService.saveClientCountry(request.getRemoteAddr());
        return new ResponseEntity(paymentService.createPayment(paymentsRequest, type, currency), HttpStatus.OK);
    }

    @PostMapping("cancel/{id}")
    public ResponseEntity<EntityModel<CancelPaymentResponse>> cancelPayment(
            @PathVariable(value = "id") Integer id) throws ResourceNotFoundException, BadInputException {
        EntityModel<CancelPaymentResponse> entityModel = cancelPaymentAssembler.toModel(paymentService.cancelPayment(id));
        return new ResponseEntity(entityModel, HttpStatus.OK);
    }

    @GetMapping
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
