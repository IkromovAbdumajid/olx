package com.comapny.dto.auth;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class RegistrationDTO {
    @NotEmpty(message = "Kalla name qani")
    private String name;
    @NotEmpty(message = "Kalla surname qani")
    @Size(min = 3, max = 15, message = "3-15 oralig'ida bo'lishi kerak mazgi.")
    private String surname;
    @Email
    private String email;
    @NotBlank(message = "Login xato mazgi")
    private String login;
    private String password;
}
