package com.mol_ferma.web.dto;

import com.mol_ferma.web.annotations.ValidPassword;
import com.mol_ferma.web.annotations.ValidPhoneNumber;
import com.mol_ferma.web.enums.RoleName;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RegistrationDto {
    private Long id;
    private String username;
    @Email(message = "Адрес электронной почты, должен соответствовать формату pochta@gmail.com")
    @NotEmpty
    private String email;
    @ValidPassword(message = "Пароль должен содержать как минимум 8 символов, включая заглавные, строчные латинские буквы и цифры")
    @NotEmpty
    private String password;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @ValidPhoneNumber(message = "Пожалуйста, введите корректный номер телефона Казахстана, пример +77477730324")
    @NotEmpty
    private String phoneNumber;
    private RoleName role;
}
