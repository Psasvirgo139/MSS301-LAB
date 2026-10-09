package com.fudn.customer_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record RegisterRequest(
        String customerName,
        String fullName,
        String telephone,
        String phone,

        @NotBlank(message = "Email is required")
        @Email(message = "Email is invalid")
        String email,

        LocalDate customerBirthday,

        @NotBlank(message = "Password is required")
        String password) {

    public String getEffectiveName() {
        if (customerName != null && !customerName.isBlank()) return customerName;
        if (fullName != null && !fullName.isBlank()) return fullName;
        return "New Customer";
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
