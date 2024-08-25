package com.mol_ferma.web.dto;

import com.mol_ferma.web.enums.RoleName;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RegistrationDto {
    private Long id;
    private String username;
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty
    private String phoneNumber;
    private RoleName role;
}
