package com.mol_ferma.web.dto;

import com.mol_ferma.web.annotations.ValidEmail;
import com.mol_ferma.web.annotations.ValidUserFirstname;
import com.mol_ferma.web.annotations.ValidPassword;
import com.mol_ferma.web.annotations.ValidPhoneNumber;
import com.mol_ferma.web.enums.RoleName;

import com.mol_ferma.web.models.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegistrationDto {
    private Long id;
    private String username;
    @ValidEmail(message = "Адрес электронной почты, должен соответствовать формату pochta@gmail.com")
    @NotEmpty
    private String email;
    @ValidPassword(message = "Пароль должен содержать как минимум 8 символов, включая заглавные, строчные латинские буквы и цифры")
    @NotEmpty
    private String password;
    @ValidUserFirstname
    @NotEmpty
    private String firstName;
    @ValidUserFirstname
    @NotEmpty
    private String lastName;
    @ValidPhoneNumber(message = "Пожалуйста, введите корректный номер телефона Казахстана, пример +77477730324")
    @NotEmpty
    private String phoneNumber;
    private Boolean enabled;
    private Role role;
}
