package com.mol_ferma.web.dto;

import com.mol_ferma.web.enums.RoleName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleDto {
    private int id;
    private RoleName name;
}
