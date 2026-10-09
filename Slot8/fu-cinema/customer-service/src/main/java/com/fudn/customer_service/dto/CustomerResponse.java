package com.fudn.customer_service.dto;

import com.fudn.customer_service.model.Customer;
import com.fudn.customer_service.model.CustomerStatus;

import java.time.LocalDate;

public record CustomerResponse(
        Long customerId,
        Long id,
        String customerName,
        String fullName,
        String telephone,
        String phone,
        String email,
        String username,
        LocalDate customerBirthday,
        CustomerStatus customerStatus) {

    public static CustomerResponse from(Customer c) {
        if (c == null) return null;
        return new CustomerResponse(
                c.getCustomerId(),
                c.getCustomerId(),
                c.getCustomerName(),
                c.getCustomerName(),
                c.getTelephone(),
                c.getTelephone(),
                c.getEmail(),
                c.getEmail(),
                c.getCustomerBirthday(),
                c.getCustomerStatus());
    }
}
