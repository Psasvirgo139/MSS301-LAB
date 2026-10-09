package com.fudn.customer_service.dto;

public record LoginRequest(
        String email,
        String username,
        String password) {

    public String getEffectiveEmail() {
        if (email != null && !email.isBlank()) return email;
        if (username != null && !username.isBlank()) return username;
        return "";
    }
}
