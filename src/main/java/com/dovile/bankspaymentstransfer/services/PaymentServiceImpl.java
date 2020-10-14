package com.dovile.bankspaymentstransfer.services;

import com.dovile.bankspaymentstransfer.domain.request.PaymentsRequest;
import com.dovile.bankspaymentstransfer.domain.response.CancelPaymentResponse;
import com.dovile.bankspaymentstransfer.domain.response.PaymentResponse;
import com.dovile.bankspaymentstransfer.domain.response.PaymentsIdResponse;
import com.dovile.bankspaymentstransfer.entities.CancelPaymentEntity;
import com.dovile.bankspaymentstransfer.entities.CurrencyDataEntity;
import com.dovile.bankspaymentstransfer.entities.PaymentTypeEntity;
import com.dovile.bankspaymentstransfer.entities.PaymentsEntity;
import com.dovile.bankspaymentstransfer.exceptions.BadInputException;
import com.dovile.bankspaymentstransfer.exceptions.ResourceNotFoundException;
import com.dovile.bankspaymentstransfer.repositories.CancelPaymentEntityRepository;
import com.dovile.bankspaymentstransfer.repositories.CurrencyDataEntityRepository;
import com.dovile.bankspaymentstransfer.repositories.PaymentTypeEntityRepository;
import com.dovile.bankspaymentstransfer.repositories.PaymentsEntityRepository;
import com.dovile.bankspaymentstransfer.validator.checkiban_biccodes.CheckBicCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Dovile Barkauskaite <barkauskaite.dovile@gmail.com>
 */
@Service
public class PaymentServiceImpl implements PaymentService {

    private final static Logger logger = Logger.getLogger(PaymentServiceImpl.class.getName());

    @Autowired
    PaymentsEntityRepository paymentsEntityRepository;

    @Autowired
    PaymentTypeEntityRepository paymentTypeEntityRepository;

    @Autowired
    CurrencyDataEntityRepository currencyDataEntityRepository;

    @Autowired
    CancelPaymentEntityRepository cancelPaymentEntityRepository;

    @Override
    public PaymentResponse createPayment(PaymentsRequest request, String type, String currency) throws ResourceNotFoundException, BadInputException {
        PaymentsEntity paymentsEntity = new PaymentsEntity();
        PaymentTypeEntity paymentTypeEntity = paymentTypeEntityRepository.findByTypeName(type);
        if (paymentTypeEntity != null) {
            paymentsEntity.setPaymentType(paymentTypeEntity);
        } else {
            logger.warning("Bad TYPE");
            throw new ResourceNotFoundException("Please insert a correct Type");
        }

        CurrencyDataEntity currencyDataEntity = currencyDataEntityRepository.findByName(currency);
        if (currencyDataEntity != null) {
            paymentsEntity.setCurrencyData(currencyDataEntity);
            if (type.equals("TYPE1") && currency.equals("EUR")) {
                paymentsEntity.setCurrencyData(currencyDataEntity);
                if (request.getAdditionalField() == null) {
                    throw new BadInputException("Please enter Addition field");
                } else {
                    paymentsEntity.setAdditionalField(request.getAdditionalField());
                }
            } else if (type.equals("TYPE2") && currency.equals("USD")) {
                paymentsEntity.setCurrencyData(currencyDataEntity);
                paymentsEntity.setAdditionalField(request.getAdditionalField());
            } else if (type.equals("TYPE3")) {
                paymentsEntity.setCurrencyData(currencyDataEntity);
                if (!(new CheckBicCode().isValidBicCode(request.getAdditionalField()))) {
                    throw new BadInputException("Please enter right Bic Code");
                } else {
                    paymentsEntity.setAdditionalField(request.getAdditionalField());
                }
            } else {
                throw new BadInputException("Bad currency type");
            }
        } else {
            logger.warning("Bad Currency");
            throw new ResourceNotFoundException("Please insert a correct Currency");
        }
        paymentsEntity.setAmount(request.getAmount());
        paymentsEntity.setDebtorIban(request.getDebtorIban());
        paymentsEntity.setCreditorIban(request.getCreditorIban());
        PaymentsEntity newPayment = paymentsEntityRepository.save(paymentsEntity);
        logger.info("Create new payment");
        return new PaymentResponse(newPayment.getId(), newPayment.getAmount(),
                newPayment.getDebtorIban(), newPayment.getCreditorIban(), newPayment.getAdditionalField());
    }

    @Transactional
    @Override
    public CancelPaymentResponse cancelPayment(Integer id) throws ResourceNotFoundException {
        PaymentsEntity paymentsEntity = paymentsEntityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found on: " + id));

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date realDate = new Date();

        //If state is false
        if (!paymentsEntity.getStatus()) {
            throw new ResourceNotFoundException("You can't cancel the payment is canceleted");
        }
        //first check time
        if (format.format(paymentsEntity.getPaymentDate()).compareTo(format.format(realDate)) < 0) {
            throw new ResourceNotFoundException("You can't cancel the payment time is off");
        }
        //calculate hours and cancelfee
        long diff = (realDate.getTime() - paymentsEntity.getPaymentDate().getTime()) / (60 * 60 * 1000) % 24;
        BigDecimal fee = new BigDecimal(diff).multiply(paymentsEntity.getPaymentType().getCoefficient());
        CancelPaymentEntity cancelPaymentEntity = new CancelPaymentEntity();
        cancelPaymentEntity.setPayments(paymentsEntity);
        cancelPaymentEntity.setCancelFee(fee);

        //Cancel payment create and update payment status;
        paymentsEntity.setStatus(false);
        paymentsEntityRepository.save(paymentsEntity);
        CancelPaymentEntity updateCancel = cancelPaymentEntityRepository.save(cancelPaymentEntity);
        logger.info("Create cancel payment");
        return new CancelPaymentResponse(updateCancel.getPayments().getId(), updateCancel.getCancelFee());
    }

    @Override
    public List<PaymentsIdResponse> getAllPaymentsId() {
        List<PaymentsIdResponse> paymentsIdResponseList = new ArrayList<>();
        for (PaymentsEntity p : paymentsEntityRepository.findByStatus(true)) {
            paymentsIdResponseList.add(new PaymentsIdResponse(p.getId()));
        }
        return paymentsIdResponseList;
    }

    @Override
    public CancelPaymentResponse getCancelPaymentById(Integer id) throws ResourceNotFoundException {
        CancelPaymentEntity c = cancelPaymentEntityRepository.findPaymentID(id);
        if (c == null) {
            throw new ResourceNotFoundException("There isn't cancel this payment");
        }
        return new CancelPaymentResponse(c.getPayments().getId(), c.getCancelFee());
    }

}
