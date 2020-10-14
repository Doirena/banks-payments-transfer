package com.dovile.bankspaymentstransfer.assemblers;

import com.dovile.bankspaymentstransfer.controller.PaymentController;
import com.dovile.bankspaymentstransfer.domain.response.PaymentResponse;
import com.dovile.bankspaymentstransfer.exceptions.ResourceNotFoundException;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PaymentModelAssembler implements RepresentationModelAssembler<PaymentResponse, EntityModel<PaymentResponse>> {

    @Override
    public EntityModel<PaymentResponse> toModel(PaymentResponse paymentResponse) {

        try {
            return EntityModel.of(paymentResponse,
                    linkTo(methodOn(PaymentController.class).cancelPayment(paymentResponse.getPaymentId())).withRel("You can cancel payment"),
                    linkTo(methodOn(PaymentController.class).getCancelPaymentById(paymentResponse.getPaymentId())).withRel("Canceled payment by id"),
                    linkTo(methodOn(PaymentController.class).getAllExistPayments()).withRel("all active payments"));
        } catch (ResourceNotFoundException e) {
            e.getMessage();
        }
        return null;
    }
}
