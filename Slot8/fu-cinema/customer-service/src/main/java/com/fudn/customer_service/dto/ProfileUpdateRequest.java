package com.fudn.customer_service.dto;

import jakarta.validation.constraints.Email;

import java.time.LocalDate;

public record ProfileUpdateRequest(
        String customerName,
        String fullName,
        String telephone,
        String phone,
        String email,
        LocalDate customerBirthday) {

    public String getEffectiveName() {
        if (customerName != null && !customerName.isBlank()) return customerName;
        if (fullName != null && !fullName.isBlank()) return fullName;
        return "Updated Customer";
    }

    public String getEffectivePhone() {
        if (telephone != null && !telephone.isBlank()) return telephone;
        if (phone != null && !phone.isBlank()) return phone;
        return "0912345678";
    }

    public LocalDate getEffectiveBirthday() {
        return customerBirthday != null ? customerBirthday : LocalDate.of(2000, 1, 1);
    }
}
