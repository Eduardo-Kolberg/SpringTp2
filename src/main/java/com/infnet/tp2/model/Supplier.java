package com.infnet.tp2.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class Supplier {
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "CNPJ is required")
    @Pattern(regexp = "\\d{14}", message = "CNPJ must be 14 digits")
    private String cnpj;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @Pattern(regexp = "\\d{10,11}", message = "Phone must be 10-11 digits")
    private String phone;

    private String address;
}
