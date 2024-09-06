package com.mol_ferma.web.dto;

import com.mol_ferma.web.annotations.ValidEmail;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ForgotEmailForm {
    @ValidEmail(message = "Адрес электронной почты, должен соответствовать формату pochta@gmail.com")
    @NotEmpty
    private String email;
}
