package com.dovile.bankspaymentstransfer.services;

import com.dovile.bankspaymentstransfer.domain.request.PaymentsRequest;
import com.dovile.bankspaymentstransfer.domain.response.CancelPaymentResponse;
import com.dovile.bankspaymentstransfer.domain.response.PaymentResponse;
import com.dovile.bankspaymentstransfer.domain.response.PaymentsIdResponse;
import com.dovile.bankspaymentstransfer.entities.*;
import com.dovile.bankspaymentstransfer.exceptions.BadInputException;
import com.dovile.bankspaymentstransfer.exceptions.ResourceNotFoundException;
import com.dovile.bankspaymentstransfer.repositories.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;


import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

/**
 * @author Dovile Barkauskaite <barkauskaite.dovile@gmail.com>
 */
@RunWith(MockitoJUnitRunner.Silent.class)
public class PaymentServiceImplTest {
    @Spy
    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Mock
    private PaymentsEntityRepository paymentsEntityRepository;
    @Mock
    private CancelPaymentEntityRepository cancelPaymentEntityRepository;
    @Mock
    private PaymentTypeEntityRepository paymentTypeEntityRepository;
    @Mock
    private CurrencyDataEntityRepository currencyDataEntityRepository;
    @Mock
    ClientCountryEntityRepository clientCountryEntityRepository;


    public PaymentServiceImplTest() {
    }

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createPayment() throws ResourceNotFoundException, BadInputException {
        PaymentTypeEntity typeEntity = new PaymentTypeEntity(1, "TYPE1", (double) (0.005));
        CurrencyDataEntity currencyDataEntity = new CurrencyDataEntity(1, "EUR", (double) (1.0));

        PaymentsEntity paymentsEntity = new PaymentsEntity(1, (double) (20), "LT647044001231465456",
                "LT647044001231465456","payment", "KUSRLT24", true, new Date(),
                currencyDataEntity, typeEntity);
        ClientCountryEntity clientCountryEntity = new ClientCountryEntity("24.48.0.1", "Canada");
        when(clientCountryEntityRepository.save(clientCountryEntity)).thenReturn(clientCountryEntity);
        when(paymentTypeEntityRepository.findByTypeName(typeEntity.getTypeName())).thenReturn(Optional.of(typeEntity));
        when(currencyDataEntityRepository.findByName(currencyDataEntity.getName())).thenReturn(Optional.of(currencyDataEntity));

        given(paymentsEntityRepository.save(paymentsEntity)).willAnswer(invocation -> invocation.getArgument(1));

        PaymentsRequest paymentsRequest = new PaymentsRequest("20", "LT647044001231465456",
                "LT647044001231465456","payment", "KUSRLT24");
        String ipAddress = "24.48.0.1";
        PaymentResponse expectedPayment = paymentService.createPayment(paymentsRequest, typeEntity.getTypeName(), currencyDataEntity.getName(), ipAddress);

        assertThat(expectedPayment).isNotNull();
        assertEquals(expectedPayment.getAmount(), paymentsEntity.getAmount());
        verify(paymentsEntityRepository).save(any(PaymentsEntity.class));
    }

    @Test
    public void createPayment_BAD_Currency() throws ResourceNotFoundException, BadInputException {
        PaymentTypeEntity typeEntity = new PaymentTypeEntity(1, "TYPE1", (double) (0.005));
        CurrencyDataEntity currencyDataEntity = new CurrencyDataEntity(1, "EUR1", (double) (1.0));

        ClientCountryEntity clientCountryEntity = new ClientCountryEntity("24.48.0.1", "Canada");
        when(clientCountryEntityRepository.save(clientCountryEntity)).thenReturn(clientCountryEntity);
        when(paymentTypeEntityRepository.findByTypeName(typeEntity.getTypeName())).thenReturn(Optional.of(typeEntity));

        PaymentsRequest paymentsRequest = new PaymentsRequest("20", "LT647044001231465456",
                "LT647044001231465456","payment", "KUSRLT24");
        String ipAddress = "24.48.0.1";
        try {
            paymentService.createPayment(paymentsRequest, typeEntity.getTypeName(), currencyDataEntity.getName(), ipAddress);
        } catch (BadInputException | ResourceNotFoundException e) {
            assertEquals(e.getMessage(), "Please insert a correct Currency");
        }
    }

    @Test
    public void createPayment_BAD_type() throws ResourceNotFoundException, BadInputException {
        PaymentTypeEntity typeEntity = new PaymentTypeEntity(1, "TYPE1", (double) (0.005));
        CurrencyDataEntity currencyDataEntity = new CurrencyDataEntity(1, "EUR", (double) (1.0));

        ClientCountryEntity clientCountryEntity = new ClientCountryEntity("24.48.0.1", "Canada");
        when(clientCountryEntityRepository.save(clientCountryEntity)).thenReturn(clientCountryEntity);
        PaymentsRequest paymentsRequest = new PaymentsRequest("20", "LT647044001231465456",
                "LT647044001231465456","payment", "KUSRLT24");
        String ipAddress = "24.48.0.1";
        try {
            paymentService.createPayment(paymentsRequest, typeEntity.getTypeName(), currencyDataEntity.getName(), ipAddress);
        } catch (BadInputException | ResourceNotFoundException e) {
            assertEquals(e.getMessage(), "Please insert a correct Type");
        }
    }

    @Test
    public void cancelPayment_OK() throws ResourceNotFoundException, BadInputException {
        Integer paymentId = 1;

        PaymentTypeEntity typeEntity = new PaymentTypeEntity(1, "TYPE1", (double) (0.005));
        CurrencyDataEntity currencyDataEntity = new CurrencyDataEntity(1, "EUR", (double) (1.0));

        PaymentsEntity paymentsEntity = new PaymentsEntity(1, (double) (20), "LT647044001231465456",
                "LT647044001231465456","payment", "KUSRLT24", true, new Date(),
                currencyDataEntity, typeEntity);
        CancelPaymentEntity cancelPaymentEntity = new CancelPaymentEntity(null, (double) 0.0, paymentsEntity);

        when(paymentsEntityRepository.findById(paymentId)).thenReturn(Optional.of(paymentsEntity));
        given(cancelPaymentEntityRepository.save(cancelPaymentEntity)).willAnswer(invocation -> invocation.getArgument(1));

        CancelPaymentResponse expectedCancelPayment = paymentService.cancelPayment(paymentsEntity.getId());
        assertThat(expectedCancelPayment).isNotNull();
        //Should be 0;
        assertEquals(expectedCancelPayment.getCancelFee(), cancelPaymentEntity.getCancelFee());
        verify(cancelPaymentEntityRepository).save(any(CancelPaymentEntity.class));
    }

    @Test
    public void cancelPayment_BadInput_Payment_Time_Off() throws ParseException {
        Integer paymentId = 1;
        String dt = "2020-10-15";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date paymentDate=sdf.parse(dt);
        PaymentTypeEntity typeEntity = new PaymentTypeEntity(paymentId, "TYPE1", (double) (0.005));
        CurrencyDataEntity currencyDataEntity = new CurrencyDataEntity(1, "EUR", (double) (1.0));

        PaymentsEntity paymentsEntity = new PaymentsEntity(1, (double) (20), "LT647044001231465456",
                "LT647044001231465456","payment", "KUSRLT24", true, paymentDate,
                currencyDataEntity, typeEntity);

        when(paymentsEntityRepository.findById(paymentId)).thenReturn(Optional.of(paymentsEntity));
        try {
            paymentService.cancelPayment(paymentId);
        } catch (BadInputException | ResourceNotFoundException e) {
            assertEquals(e.getMessage(), "You can't cancel the payment time is off");
        }
    }

    @Test
    public void cancelPayment_Payment_Was_Cenceled_Before() throws ParseException {
        Integer paymentId = 1;
        PaymentTypeEntity typeEntity = new PaymentTypeEntity(paymentId, "TYPE1", (double) (0.005));
        CurrencyDataEntity currencyDataEntity = new CurrencyDataEntity(1, "EUR", (double) (1.0));

        PaymentsEntity paymentsEntity = new PaymentsEntity(1, (double) (20), "LT647044001231465456",
                "LT647044001231465456","payment", "KUSRLT24", false, new Date(),
                currencyDataEntity, typeEntity);

        when(paymentsEntityRepository.findById(paymentId)).thenReturn(Optional.of(paymentsEntity));
        try {
            paymentService.cancelPayment(paymentId);
        } catch (BadInputException | ResourceNotFoundException e) {
            assertEquals(e.getMessage(), "You can't cancel the payment is canceleted");
        }
    }

    @Test
    public void cancelPayment_when_PaymentID_not_Found() {
        Integer paymentId = 1;
        try {
            paymentService.cancelPayment(paymentId);
        } catch (ResourceNotFoundException | BadInputException e) {
            assertEquals(e.getMessage(), "Payment not found on: " + paymentId);
        }
    }

    @Test
    public void getAllPaymentsId() {
        List<PaymentsEntity> paymentsEList = new ArrayList();
        PaymentTypeEntity typeEntity = new PaymentTypeEntity(1, "TYPE1", (double) (0.005));
        CurrencyDataEntity currencyDataEntity = new CurrencyDataEntity(1, "EUR", (double) (1.0));

        paymentsEList.add(new PaymentsEntity(1, (double) (10), "LT647044001231465456", "LT647044001231465456",
                "payment", "KUSRLT24", true, new Date(), currencyDataEntity, typeEntity));
        paymentsEList.add(new PaymentsEntity(2, (double) (20), "LT647044001231465456", "LT647044001231465456",
                "payment", "KUSRLT24", true, new Date(), currencyDataEntity, typeEntity));
        paymentsEList.add(new PaymentsEntity(3, (double) (30), "LT647044001231465456", "LT647044001231465456",
                "payment", "KUSRLT24", true, new Date(), currencyDataEntity, typeEntity));

        given(paymentsEntityRepository.findByStatus(true)).willReturn(paymentsEList);

        List<PaymentsIdResponse> expectedIdResponses = paymentService.getAllPaymentsId();
        assertEquals(3, expectedIdResponses.size());
    }

    @Test
    public void getCancelPaymentById() throws ResourceNotFoundException {
        Integer paymentId = 1;

        PaymentsEntity paymentsEntity = new PaymentsEntity(paymentId, (double) 200, false, new Date());
        CancelPaymentEntity cancelE = new CancelPaymentEntity(null, (double) 20, paymentsEntity);
        when(cancelPaymentEntityRepository.findPaymentID(paymentId)).thenReturn(java.util.Optional.of(cancelE));

        CancelPaymentResponse expectedCancelPayment = paymentService.getCancelPaymentById(paymentId);

        assertEquals(cancelE.getCancelFee(), expectedCancelPayment.getCancelFee());
        assertEquals(cancelE.getPayments().getId(), expectedCancelPayment.getPaymentId());
    }

    @Test
    public void getCancelError_when_PaymentID_notExist() {
        Integer paymentId = 1;
        try {
            paymentService.getCancelPaymentById(paymentId);
        } catch (ResourceNotFoundException e) {
            assertEquals(e.getMessage(), "There isn't cancel this payment");
        }
    }
}