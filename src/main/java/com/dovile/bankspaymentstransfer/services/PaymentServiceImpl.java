package com.dovile.bankspaymentstransfer.services;

import com.dovile.bankspaymentstransfer.domain.request.PaymentsRequest;
import com.dovile.bankspaymentstransfer.domain.response.CancelPaymentResponse;
import com.dovile.bankspaymentstransfer.domain.response.PaymentResponse;
import com.dovile.bankspaymentstransfer.domain.response.PaymentsIdResponse;
import com.dovile.bankspaymentstransfer.entities.CurrencyDataEntity;
import com.dovile.bankspaymentstransfer.entities.PaymentTypeEntity;
import com.dovile.bankspaymentstransfer.entities.CancelPaymentEntity;
import com.dovile.bankspaymentstransfer.entities.PaymentsEntity;
import com.dovile.bankspaymentstransfer.exceptions.BadInputException;
import com.dovile.bankspaymentstransfer.exceptions.ResourceNotFoundException;
import com.dovile.bankspaymentstransfer.repositories.CurrencyDataEntityRepository;
import com.dovile.bankspaymentstransfer.repositories.PaymentTypeEntityRepository;
import com.dovile.bankspaymentstransfer.repositories.PaymentsEntityRepository;
import com.dovile.bankspaymentstransfer.repositories.CancelPaymentEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author Dovile Barkauskaite <barkauskaite.dovile@gmail.com>
 */
@Service
public class PaymentServiceImpl implements PaymentService {

    private final static Logger logger = Logger.getLogger(PaymentServiceImpl.class.getName());

    private final long ONE_HOUR = 60 * 60 * 1000;

    @Autowired
    PaymentsEntityRepository paymentsEntityRepository;
    @Autowired
    PaymentTypeEntityRepository paymentTypeEntityRepository;
    @Autowired
    CurrencyDataEntityRepository currencyDataEntityRepository;
    @Autowired
    CancelPaymentEntityRepository cancelPaymentEntityRepository;

    /**
     * {@inheritDoc}
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
     * {@inheritDoc}
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
     * {@inheritDoc}
     */
    @Override
    public List<PaymentsIdResponse> getAllPaymentsId() {
        List<PaymentsIdResponse> paymentsIdResponseList = paymentsEntityRepository.findByStatus(true).stream()
                .map(i->new PaymentsIdResponse(i.getId()))
                .collect(Collectors.toList());
        return paymentsIdResponseList;
    }

    /**
     * {@inheritDoc}
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
        Long diff = (new Date().getTime() - paymentDate.getTime()) / ONE_HOUR % 24;
        Double fee = (diff.doubleValue())*coefficient;
        return fee;
    }
}
