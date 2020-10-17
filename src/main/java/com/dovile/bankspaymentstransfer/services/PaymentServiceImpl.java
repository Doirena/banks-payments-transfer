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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * @author Dovile Barkauskaite <barkauskaite.dovile@gmail.com>
 */
@Service
public class PaymentServiceImpl implements PaymentService {

    private final static Logger logger = Logger.getLogger(PaymentServiceImpl.class.getName());

    private final long HOURS = 60 * 60 * 1000;

    @Autowired
    PaymentsEntityRepository paymentsEntityRepository;

    @Autowired
    PaymentTypeEntityRepository paymentTypeEntityRepository;

    @Autowired
    CurrencyDataEntityRepository currencyDataEntityRepository;

    @Autowired
    CancelPaymentEntityRepository cancelPaymentEntityRepository;

    /**
     *
     * @param paymentsRequest
     * @param type
     * @param currency
     * @return new payment
     * @throws ResourceNotFoundException
     * @throws BadInputException
     */
    @Override
    public PaymentResponse createPayment(PaymentsRequest paymentsRequest, String type, String currency)
            throws ResourceNotFoundException, BadInputException {
        type = type.replaceAll("\\s", "").toUpperCase(Locale.ROOT);
        currency = currency.replaceAll("\\s", "").toUpperCase(Locale.ROOT);
        PaymentsEntity paymentsEntity = new PaymentsEntity();

        PaymentTypeEntity paymentTypeEntity = paymentTypeEntityRepository.findByTypeName(type).
                orElseThrow(() -> new ResourceNotFoundException("Please insert a correct Type"));

        paymentsEntity.setPaymentType(paymentTypeEntity);

        CurrencyDataEntity currencyDataEntity = currencyDataEntityRepository.findByName(currency).
                orElseThrow(() -> new ResourceNotFoundException("Please insert a correct Currency"));

        paymentsEntity.setCurrencyData(currencyDataEntity);
        paymentsEntity.setDetails(paymentsRequest.getDetails());
        paymentsEntity.setBic_code(paymentsRequest.getBic_code());
        paymentsEntity.setAmount(Double.parseDouble(paymentsRequest.getAmount()));
        paymentsEntity.setDebtorIban(paymentsRequest.getDebtorIban());
        paymentsEntity.setCreditorIban(paymentsRequest.getCreditorIban());
        paymentsEntityRepository.save(paymentsEntity);
        logger.info("Create new payment");
        return new PaymentResponse(paymentsEntity.getId(), paymentsEntity.getAmount(),
                paymentsEntity.getDebtorIban(), paymentsEntity.getCreditorIban(),
                paymentsEntity.getDetails(), paymentsEntity.getBic_code());
    }

    /**
     *
     * @param paymentId
     * @return new Canceled payment with canceled fee.
     * @throws ResourceNotFoundException
     * @throws BadInputException
     */
    @Transactional
    @Override
    public CancelPaymentResponse cancelPayment(Integer paymentId) throws ResourceNotFoundException, BadInputException {
        PaymentsEntity paymentsEntity = paymentsEntityRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found on: " + paymentId));

        //If state is false
        if (!paymentsEntity.getStatus()) {
            throw new BadInputException("You can't cancel the payment is canceleted");
        }
        //first check time
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if (format.format(paymentsEntity.getPaymentDate()).compareTo(format.format(new Date())) < 0) {
            throw new BadInputException("You can't cancel the payment time is off");
        }

        CancelPaymentEntity cancelPaymentEntity = new CancelPaymentEntity();
        cancelPaymentEntity.setPayments(paymentsEntity);
        cancelPaymentEntity.setCancelFee(
                calcCancelFee(paymentsEntity.getPaymentDate(), paymentsEntity.getPaymentType().getCoefficient()));

        //Cancel payment create and update payment status;
        paymentsEntity.setStatus(false);
        paymentsEntityRepository.save(paymentsEntity);
        cancelPaymentEntityRepository.save(cancelPaymentEntity);
        logger.info("Create cancel payment");
        return new CancelPaymentResponse(cancelPaymentEntity.getPayments().getId(), cancelPaymentEntity.getCancelFee());
    }

    /**
     *
     * @return list of active payments. Returns just payment id.
     */
    @Override
    public List<PaymentsIdResponse> getAllPaymentsId() {
        List<PaymentsIdResponse> paymentsIdResponseList = new ArrayList<>();
        for (PaymentsEntity p : paymentsEntityRepository.findByStatus(true)) {
            paymentsIdResponseList.add(new PaymentsIdResponse(p.getId()));
        }
        return paymentsIdResponseList;
    }

    /**
     *
     * @param paymentId
     * @return canceled payment by payments id. Returns value: payments id and canceled fee
     * @throws ResourceNotFoundException
     */
    @Override
    public CancelPaymentResponse getCancelPaymentById(Integer paymentId) throws ResourceNotFoundException {
        CancelPaymentEntity c = cancelPaymentEntityRepository.findPaymentID(paymentId).
                orElseThrow(() -> new ResourceNotFoundException("There isn't cancel this payment"));
        return new CancelPaymentResponse(c.getPayments().getId(), c.getCancelFee());
    }

    /**
     *
     * @param paymentDate
     * @param coefficient
     * @return Cancel payment fee, which is get by formula: h*k
     * k is coefficient, which we get from Payment Type table. It is by default according to each type;
     * h is hours, which get calculate diff between payment create time and real time.
     */
    private Double calcCancelFee(Date paymentDate, Double coefficient){
        long diff = (new Date().getTime() - paymentDate.getTime()) / HOURS % 24;
        Double fee = (double)(diff)*coefficient;
        return fee;
    }
}
