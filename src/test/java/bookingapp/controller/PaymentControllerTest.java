package bookingapp.controller;

import static bookingapp.test.TestUtils.PAGEABLE;
import static bookingapp.test.TestUtils.PAYMENT_PAID_RESPONSE;
import static bookingapp.test.TestUtils.PAYMENT_PENDING_RESPONSE;
import static bookingapp.test.TestUtils.RENEWED_PAYMENT_PENDING_RESPONSE;
import static bookingapp.test.TestUtils.SECOND_PAYMENT_PENDING_RESPONSE;
import static bookingapp.test.TestUtils.SESSION_ID;
import static bookingapp.test.TestUtils.USER_CUSTOMER;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import bookingapp.dto.payment.PaymentRequestDto;
import bookingapp.dto.payment.PaymentResponse;
import bookingapp.service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class PaymentControllerTest {
    protected static MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PaymentService paymentService;

    @BeforeAll
    static void beforeAll(
            @Autowired WebApplicationContext applicationContext,
            @Autowired DataSource dataSource
    ) throws SQLException {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
        teardown(dataSource);
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/payment/add-default-three-payment.sql")
            );
        }
    }

    @AfterAll
    static void afterAll(@Autowired DataSource dataSource) {
        teardown(dataSource);
    }

    @SneakyThrows
    private static void teardown(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/payment/delete-payment-related-data.sql")
            );
        }
    }

    @Test
    @DisplayName("Verify getPayments() method for admin user")
    @WithUserDetails(value = "admin@example.com",
            userDetailsServiceBeanName = "customUserDetailsService")
    void getPayments_ValidRequest_ReturnPaymentsResponse() throws Exception {
        when(paymentService.getPayments(2L, PAGEABLE))
                .thenReturn(List.of(PAYMENT_PENDING_RESPONSE));

        MvcResult result = mockMvc.perform(get("/payments/")
                        .param("user_id", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        PaymentResponse[] paymentResponses = objectMapper.readValue(
                result.getResponse().getContentAsString(), PaymentResponse[].class);
        assertNotNull(paymentResponses[0]);
    }

    @Test
    @DisplayName("Verify getPayments() method for customer user")
    @WithUserDetails(value = "john.doe@example.com",
            userDetailsServiceBeanName = "customUserDetailsService")
    void getPayments_ValidAuthenticatedUser_ReturnPayments() throws Exception {
        when(paymentService.getPayments(2L, PAGEABLE))
                .thenReturn(List.of(PAYMENT_PENDING_RESPONSE));

        MvcResult result = mockMvc.perform(get("/payments/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        PaymentResponse[] paymentResponses = objectMapper.readValue(
                result.getResponse().getContentAsString(), PaymentResponse[].class);
        assertNotNull(paymentResponses[0]);
    }

    @Test
    @DisplayName("Verify createPayment() method")
    @WithUserDetails(value = "john.doe@example.com",
            userDetailsServiceBeanName = "customUserDetailsService")
    void createPayment_ValidRequest_ValidResponse() throws Exception {
        PaymentRequestDto requestDto = new PaymentRequestDto(1L);
        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        when(paymentService.initiatePayment(USER_CUSTOMER, requestDto))
                .thenReturn(SECOND_PAYMENT_PENDING_RESPONSE);

        mockMvc.perform(post("/payments/")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Verify renewPayment() method")
    @WithUserDetails(value = "john.doe@example.com",
            userDetailsServiceBeanName = "customUserDetailsService")
    void renewPayment_ValidRequest_ValidResponse() throws Exception {
        when(paymentService.renewPaymentSession("sessionIdExpired", USER_CUSTOMER))
                .thenReturn(RENEWED_PAYMENT_PENDING_RESPONSE);

        mockMvc.perform(post("/payments/renew/")
                        .param("sessionId", "sessionIdExpired")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Verify successPayment() method")
    @WithUserDetails(value = "john.doe@example.com",
            userDetailsServiceBeanName = "customUserDetailsService")
    void successPayment_ValidRequest_ReturnPaymentResponse() throws Exception {
        when(paymentService.handleSuccessPayment(SESSION_ID)).thenReturn(PAYMENT_PAID_RESPONSE);

        mockMvc.perform(get("/payments/success/")
                        .param("sessionId", "sessionId")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Verify cancel() method")
    @WithUserDetails(value = "john.doe@example.com",
            userDetailsServiceBeanName = "customUserDetailsService")
    void cancelPayment_ValidRequest_ReturnPaymentResponse() throws Exception {
        when(paymentService.handleCancelPayment(SESSION_ID))
                .thenReturn(SECOND_PAYMENT_PENDING_RESPONSE);

        mockMvc.perform(get("/payments/cancel/")
                        .param("sessionId", "sessionId")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
