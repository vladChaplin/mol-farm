package com.mol_ferma.web.dto;

import com.mol_ferma.web.annotations.ValidPasswordMatches;
import com.mol_ferma.web.annotations.ValidPassword;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ValidPasswordMatches
public class ForgotPasswordForm {
    @ValidPassword
    @NotEmpty
    private String password;

    @NotEmpty
    private String duplicatePassword;

}
