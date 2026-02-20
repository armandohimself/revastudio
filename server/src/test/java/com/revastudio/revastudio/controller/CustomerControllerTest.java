package com.revastudio.revastudio.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.revastudio.revastudio.dto.CustomerDetailResponse;
import com.revastudio.revastudio.service.CustomerService;

/**
 * MAIN IDEA
 * You’re testing: routing + JSON + status codes + validation behavior
 */

/**
 * @WebMvcTest
 * Boots ONLY the web layer (controller + JSON), not the whole app.
 */
@WebMvcTest
class CustomerControllerTest {

    @Autowired              // Inject dependency for us Spring during runtime when we need it.
    MockMvc mockMvc;        // Lets you call your controller like a real HTTP request (w/o server running).

    @MockitoBean
    CustomerService customerService;

    @Test
    void getCustomerById_returns200_andJson() {

        UUID customerId = UUID.randomUUID();

        CustomerDetailResponse dto = new CustomerDetailResponse(
            customerId,
            "No Cap LLC",
            null,
            null,
            null,
            null);

        when(customerService.getCustomerDetail(customerId)).thenReturn(dto);

        mockMvc.perform(get("/customers/{id}", customerId))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.customerId").value(customerId.toString()))
            .andExpect(jsonPath("$.company").value("No Cap LLC"));

        verify(customerService).getCustomerDetail(customerId);
    }



}
