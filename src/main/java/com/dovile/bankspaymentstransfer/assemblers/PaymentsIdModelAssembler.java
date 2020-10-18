package com.dovile.bankspaymentstransfer.assemblers;

import com.dovile.bankspaymentstransfer.controller.PaymentController;
import com.dovile.bankspaymentstransfer.domain.response.PaymentsIdResponse;
import com.dovile.bankspaymentstransfer.exceptions.BadInputException;
import com.dovile.bankspaymentstransfer.exceptions.ResourceNotFoundException;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 *
 * @author Dovile Barkauskaite <barkauskaite.dovile@gmail.com>
 */
@Component
public class PaymentsIdModelAssembler implements RepresentationModelAssembler<PaymentsIdResponse, EntityModel<PaymentsIdResponse>> {

    @Override
    public EntityModel<PaymentsIdResponse> toModel(PaymentsIdResponse paymentsIdResponse) {

        try {
            return EntityModel.of(paymentsIdResponse,
                    linkTo(methodOn(PaymentController.class).cancelPayment(paymentsIdResponse.getPaymentId())).withRel("You can cancel payment"),
                    linkTo(methodOn(PaymentController.class).getAllExistPayments()).withRel("All active payments"));
        } catch (ResourceNotFoundException | BadInputException e) {
            e.getMessage();
        }
        return null;
    }
}
