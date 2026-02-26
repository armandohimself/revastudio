package com.revastudio.revastudio.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revastudio.revastudio.dto.CreateCustomerDto;
import com.revastudio.revastudio.dto.CustomerDetailResponse;
import com.revastudio.revastudio.entity.Customer;
import com.revastudio.revastudio.service.CustomerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    //! GET

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDetailResponse> getById(@PathVariable UUID id) {

        CustomerDetailResponse dto = customerService.getCustomerDetail(id);

        return ResponseEntity.ok(dto);
    }

    //! POST

    @PostMapping()
    public ResponseEntity<CreateCustomerDto> create(@RequestBody Customer customer) {

        CreateCustomerDto dto = customerService.createCustomer(customer);

        URI location = URI.create("/customers/" + dto.customerId());

        return ResponseEntity.created(location).body(dto);
    }


}

/**
 * 🟢 1xx – Informational

Rare in REST APIs

100 CONTINUE

101 SWITCHING_PROTOCOLS

🔵 2xx – Success

Most common:

200 OK

201 CREATED

202 ACCEPTED

204 NO_CONTENT

Other less common:

206 PARTIAL_CONTENT

🟡 3xx – Redirection

Mostly for web apps:

301 MOVED_PERMANENTLY

302 FOUND

304 NOT_MODIFIED

🟠 4xx – Client Errors

These are your most important ones for APIs:

400 BAD_REQUEST

401 UNAUTHORIZED

403 FORBIDDEN

404 NOT_FOUND

405 METHOD_NOT_ALLOWED

409 CONFLICT

415 UNSUPPORTED_MEDIA_TYPE

422 UNPROCESSABLE_ENTITY

429 TOO_MANY_REQUESTS

🔴 5xx – Server Errors

500 INTERNAL_SERVER_ERROR

502 BAD_GATEWAY

503 SERVICE_UNAVAILABLE

504 GATEWAY_TIMEOUT


ResponseEntity.ok(body)
ResponseEntity.ok()                       // 200 with no body
ResponseEntity.created(URI)               // 201
ResponseEntity.accepted()                 // 202
ResponseEntity.noContent()                // 204
ResponseEntity.badRequest()               // 400
ResponseEntity.notFound()                 // 404
ResponseEntity.unprocessableEntity()      // 422
ResponseEntity.internalServerError()      // 500

 */
