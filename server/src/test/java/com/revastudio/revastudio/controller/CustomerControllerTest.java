package com.revastudio.revastudio.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.revastudio.revastudio.dto.CreateCustomerDto;
import com.revastudio.revastudio.dto.CustomerDetailResponse;

import com.revastudio.revastudio.service.CustomerService;

/**
 * MAIN IDEA
 * You’re testing: routing + JSON + status codes + validation behavior
 */

/**
 * @WebMvcTest
 * Boots ONLY the web layer (controller + JSON), not the whole app.
 * By saying CustomerController.class, you're saying ONLY load this controller.
 * Otherwise @WebMvcTest can scan/load multiple controllers getting extra beans, ambiguous mapping, & more things to mock.
 */
@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired                          // Inject dependency for us Spring during runtime when we need it.
    MockMvc mockMvc;                    // Lets you call your controller like a real HTTP request (w/o server running).

    @MockitoBean                        // Creates a fake version of CustomerService so we can control outputs.
    CustomerService customerService;

    @Test
    void getCustomerById_returns200_andJson() throws Exception {    // ! We add throws Exception because..

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

    @Test
    void createCustomer_returns201() throws Exception {

        UUID customerId = UUID.randomUUID();

        // Pretend the service created a customer and returns the created customer DTO.
        CreateCustomerDto created = new CreateCustomerDto(customerId, "Mando LLC", null, null, null);

        when(customerService.createCustomer(any())).thenReturn(created);

        // Minimal JSON body for create DTO
        String json = """
            {
                "company": "Mando LLC",
                "pii": null,
                "address": null,
                "supportRepId": null
            }
        """;

        mockMvc.perform(post("/customers")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.customerId").value(customerId.toString()))
            .andExpect(jsonPath("$.company").value("Mando LLC"));
    }



}
