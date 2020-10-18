package com.dovile.bankspaymentstransfer.controller;

import com.dovile.bankspaymentstransfer.domain.request.PaymentsRequest;
import com.dovile.bankspaymentstransfer.domain.response.CancelPaymentResponse;
import com.dovile.bankspaymentstransfer.entities.CancelPaymentEntity;
import com.dovile.bankspaymentstransfer.entities.CurrencyDataEntity;
import com.dovile.bankspaymentstransfer.entities.PaymentTypeEntity;
import com.dovile.bankspaymentstransfer.entities.PaymentsEntity;
import com.dovile.bankspaymentstransfer.services.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/**
 *
 * @author Dovile Barkauskaite <barkauskaite.dovile@gmail.com>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PaymentControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @Test
    public void createPayment_OK() throws Exception {
        PaymentTypeEntity typeEntity = new PaymentTypeEntity(1, "TYPE3", (double) (0.005));
        CurrencyDataEntity currencyDataEntity = new CurrencyDataEntity(1, "EUR", (double) (1.0));
        PaymentsRequest paymentsRequest = new PaymentsRequest("20", "LT647044001231465456",
                "LT647044001231465456", "payment", "KUSRLT24");

        this.mockMvc.perform(MockMvcRequestBuilders.post("/payments/create")
                .param("type", typeEntity.getTypeName())
                .param("currency", currencyDataEntity.getName())
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(paymentsRequest))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andDo(print());
    }

    @Test
    public void createPayment_when_bad_type_should_not_found() throws Exception {
        PaymentTypeEntity typeEntity = new PaymentTypeEntity(1, "TYPE5", (double) (0.005));
        CurrencyDataEntity currencyDataEntity = new CurrencyDataEntity(1, "EUR", (double) (1.0));
        PaymentsRequest paymentsRequest = new PaymentsRequest("20", "LT647044001231465456",
                "LT647044001231465456", "payment", "KUSRLT24");

        this.mockMvc.perform(MockMvcRequestBuilders.post("/payments/create")
                .param("type", typeEntity.getTypeName())
                .param("currency", currencyDataEntity.getName())
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(paymentsRequest))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(content().json("{'message':'Type is invalid'}"));
    }

    @Test
    public void createPayment_when_bad_currency_should_bad_request() throws Exception {
        PaymentTypeEntity typeEntity = new PaymentTypeEntity(1, "TYPE1", (double) (0.005));
        CurrencyDataEntity currencyDataEntity = new CurrencyDataEntity(1, "EUR111", (double) (1.0));
        PaymentsRequest paymentsRequest = new PaymentsRequest("20", "LT647044001231465456",
                "LT647044001231465456", "payment", "KUSRLT24");

        this.mockMvc.perform(MockMvcRequestBuilders.post("/payments/create")
                .param("type", typeEntity.getTypeName())
                .param("currency", currencyDataEntity.getName())
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(paymentsRequest))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(content().json("{'message':'Currency should be EUR'}"));
    }

    @Test
    public void createPayment_when_bad_currency_for_type_should_bad_request() throws Exception {
        PaymentTypeEntity typeEntity = new PaymentTypeEntity(1, "TYPE2", (double) (0.005));
        CurrencyDataEntity currencyDataEntity = new CurrencyDataEntity(1, "EUR", (double) (1.0));
        PaymentsRequest paymentsRequest = new PaymentsRequest("20", "LT647044001231465456",
                "LT647044001231465456", "payment", "KUSRLT24");

        this.mockMvc.perform(MockMvcRequestBuilders.post("/payments/create")
                .param("type", typeEntity.getTypeName())
                .param("currency", currencyDataEntity.getName())
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(paymentsRequest))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(content().json("{'message':'Currency should be USD'}"));
    }

    @Test
    public void createPayment_when_type1_empty_details_should_bad_request() throws Exception {
        PaymentTypeEntity typeEntity = new PaymentTypeEntity(1, "TYPE1", (double) (0.005));
        CurrencyDataEntity currencyDataEntity = new CurrencyDataEntity(1, "EUR", (double) (1.0));
        PaymentsRequest paymentsRequest = new PaymentsRequest("20", "LT647044001231465456",
                "LT647044001231465456", "", "KUSRLT24");

        this.mockMvc.perform(MockMvcRequestBuilders.post("/payments/create")
                .param("type", typeEntity.getTypeName())
                .param("currency", currencyDataEntity.getName())
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(paymentsRequest))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(content().json("{'message':'Please fill in the details field'}"));
    }

    @Test
    public void createPayment_when_type3_empty_bic_should_bad_request() throws Exception {
        PaymentTypeEntity typeEntity = new PaymentTypeEntity(1, "TYPE3", (double) (0.005));
        CurrencyDataEntity currencyDataEntity = new CurrencyDataEntity(1, "EUR", (double) (1.0));
        PaymentsRequest paymentsRequest = new PaymentsRequest("20", "LT647044001231465456",
                "LT647044001231465456", "details", "");

        this.mockMvc.perform(MockMvcRequestBuilders.post("/payments/create")
                .param("type", typeEntity.getTypeName())
                .param("currency", currencyDataEntity.getName())
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(paymentsRequest))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(content().json("{'message':'Please enter right Bic Code'}"));
    }

    @Test
    public void createPayment_when_deb_iban_should_bad_request() throws Exception {
        PaymentTypeEntity typeEntity = new PaymentTypeEntity(1, "TYPE3", (double) (0.005));
        CurrencyDataEntity currencyDataEntity = new CurrencyDataEntity(1, "EUR", (double) (1.0));
        PaymentsRequest paymentsRequest = new PaymentsRequest("20", "LT6470440012314654561",
                "LT647044001231465456", "details", "");

        this.mockMvc.perform(MockMvcRequestBuilders.post("/payments/create")
                .param("type", typeEntity.getTypeName())
                .param("currency", currencyDataEntity.getName())
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(paymentsRequest))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(content().json("{'message':'Debitor Iban is empty or incorrect'}"));
    }

    @Test
    public void createPayment_when_cred_iban_should_bad_request() throws Exception {
        PaymentTypeEntity typeEntity = new PaymentTypeEntity(1, "TYPE3", (double) (0.005));
        CurrencyDataEntity currencyDataEntity = new CurrencyDataEntity(1, "EUR", (double) (1.0));
        PaymentsRequest paymentsRequest = new PaymentsRequest("20", "LT647044001231465456",
                "LT6470440012314654561", "details", "");

        this.mockMvc.perform(MockMvcRequestBuilders.post("/payments/create")
                .param("type", typeEntity.getTypeName())
                .param("currency", currencyDataEntity.getName())
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(paymentsRequest))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(content().json("{'message':'Credit Iban is empty or incorrect'}"));
    }

    @Test
    public void createPayment_when_amount_string_should_bad_request() throws Exception {
        PaymentTypeEntity typeEntity = new PaymentTypeEntity(1, "TYPE3", (double) (0.005));
        CurrencyDataEntity currencyDataEntity = new CurrencyDataEntity(1, "EUR", (double) (1.0));
        PaymentsRequest paymentsRequest = new PaymentsRequest("HELLO", "LT647044001231465456",
                "LT647044001231465456", "details", "");

        this.mockMvc.perform(MockMvcRequestBuilders.post("/payments/create")
                .param("type", typeEntity.getTypeName())
                .param("currency", currencyDataEntity.getName())
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(paymentsRequest))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(content().json("{'message':'Bad amount type, should be just number'}"));
    }

    @Test
    public void createPayment_when_amount_negative_should_bad_request() throws Exception {
        PaymentTypeEntity typeEntity = new PaymentTypeEntity(1, "TYPE3", (double) (0.005));
        CurrencyDataEntity currencyDataEntity = new CurrencyDataEntity(1, "EUR", (double) (1.0));
        PaymentsRequest paymentsRequest = new PaymentsRequest("-20", "LT647044001231465456",
                "LT647044001231465456", "details", "");

        this.mockMvc.perform(MockMvcRequestBuilders.post("/payments/create")
                .param("type", typeEntity.getTypeName())
                .param("currency", currencyDataEntity.getName())
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(paymentsRequest))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(content().json("{'message':'Minimum amount is 0.01'}"));
    }

    @Test
    public void createPayment_when_amount_number_after_dot_should_bad_request() throws Exception {
        PaymentTypeEntity typeEntity = new PaymentTypeEntity(1, "TYPE3", (double) (0.005));
        CurrencyDataEntity currencyDataEntity = new CurrencyDataEntity(1, "EUR", (double) (1.0));
        PaymentsRequest paymentsRequest = new PaymentsRequest("1.0009", "LT647044001231465456",
                "LT647044001231465456", "details", "");

        this.mockMvc.perform(MockMvcRequestBuilders.post("/payments/create")
                .param("type", typeEntity.getTypeName())
                .param("currency", currencyDataEntity.getName())
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(paymentsRequest))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(content().json("{'message':'Bad amount, after dot should be two numbers'}"));
    }

    @Test
    public void getCancelPaymentById_OK() throws Exception {

        PaymentsEntity paymentsEntity = new PaymentsEntity(1, (double) 200, false, new Date());
        CancelPaymentEntity cancelE = new CancelPaymentEntity(null, (double) 20, paymentsEntity);
        CancelPaymentResponse cancelPaymentResponse = new CancelPaymentResponse(1, 20.0);
        when(paymentService.getCancelPaymentById(1)).thenReturn(cancelPaymentResponse);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/payments/cancelPaymentFee/1"))
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()));
    }
}