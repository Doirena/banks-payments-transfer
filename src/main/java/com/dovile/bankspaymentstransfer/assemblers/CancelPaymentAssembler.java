package com.dovile.bankspaymentstransfer.assemblers;

import com.dovile.bankspaymentstransfer.controller.PaymentController;
import com.dovile.bankspaymentstransfer.domain.response.CancelPaymentResponse;
import com.dovile.bankspaymentstransfer.domain.response.PaymentsIdResponse;
import com.dovile.bankspaymentstransfer.exceptions.ResourceNotFoundException;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CancelPaymentAssembler implements RepresentationModelAssembler<CancelPaymentResponse, EntityModel<CancelPaymentResponse>> {

    @Override
    public EntityModel<CancelPaymentResponse> toModel(CancelPaymentResponse cancelPaymentResponse) {

        try {
            return EntityModel.of(cancelPaymentResponse,
                    linkTo(methodOn(PaymentController.class).getCancelPaymentById(cancelPaymentResponse.getPaymentId())).withSelfRel(),
                    linkTo(methodOn(PaymentController.class).getAllExistPayments()).withRel("all active payments"));
        } catch (ResourceNotFoundException e) {
            e.getMessage();
        }
        return null;
    }
}
